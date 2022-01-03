//
//  NewsListModel.swift
//  iosApp
//
//  Created by Anna Zharkova on 03.10.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import Combine
import shared

@MainActor
class NewsListModel : ObservableObject {
    lazy var newsService: NewsService = {
        let service = DI().newsService
        return service
    }()
    
    lazy var newsViewModel: NewsViewModel = {
        let newsViewModel =  NewsViewModel()
        newsViewModel.newsFlow.collect(collector: self.collector, completionHandler: {_,_ in })
        return newsViewModel
    }()
    
    @Published var items: [NewsItem] = [NewsItem]()
    
    var store =  Set<AnyCancellable>()
    
    
    private lazy var collector: Observer = {
        let collector = Observer {value in
            if let value = value as? NewsList {
                let data = value.articles
                self.processNews(data: data)
            }
        }
        return collector
    }()
    
    func loadNews() {
        newsViewModel.loadData()
    }
    
    func loadDataPub() {
     let _ =  newsService.getNewsList().sink { result in
            switch result {
            case .failure(let error):
                print(error.localizedDescription)
            default:
                break
            }
        } receiveValue: { data in
            self.items = [NewsItem]()
            self.items.append(contentsOf: data)
        }.store(in: &store)

    }
    
    func loadDataPubFlow() {
        let _ =  FlowPublisher<NewsList>(wrapper: newsViewModel.flowNewsItem).sink { result in
            switch result {
            case .failure(let error):
                print(error.localizedDescription)
            default:
                break
            }
        } receiveValue: { data in
            let data  = data.articles
            self.items = [NewsItem]()
            self.items.append(contentsOf: data)
        }.store(in: &store)
    }
    
    @MainActor
    func loadAndSetup() {
        Task {
            let newsResult = await loadAsyncNews()
            
            let result = await loadDataAsync(wrapper: newsViewModel.flowNewsItem)
            switch newsResult {
            case .success(let data):
                self.items = [NewsItem]()
                self.items.append(contentsOf: data)
            default:
                break
            }
        }
    }
    
    func loadDataAsync<T>(wrapper: AnyFlow<T>) async -> Result<T?,Error> {
        return await withCheckedContinuation{ continuation in
            wrapper.collect { result in
                continuation.resume(returning: .success(result))
            } onCompletion: { error in
                continuation.resume(returning: .failure(CustomError(error: error)))
            }

        }
    }
    
    func loadAsyncNews() async-> Result<[NewsItem],Error> {
        return await withCheckedContinuation{ continuation in
            newsService.getNewsList(completionHandler: { response, error in
                if let news = response?.content?.articles {
                    continuation.resume(returning: .success(news))
                }
                if let error = response?.errorResponse {
                    continuation.resume(returning: .failure(CustomError(error: error.message)))
                }
                if let errorReal = error {
                    continuation.resume(returning: .failure(CustomError(error: errorReal.localizedDescription)))
                }
            })
        }
    }
    
    
    func processNews(data: [NewsItem]) {
        self.items = [NewsItem]()
        self.items.append(contentsOf: data)
    }
}

typealias Collector = Kotlinx_coroutines_coreFlowCollector

class Observer: Collector {
    let callback:(Any?) -> Void
    
    init(callback: @escaping (Any?) -> Void) {
        self.callback = callback
    }
    
    func emit(value: Any?, completionHandler: @escaping (KotlinUnit?, Error?) -> Void) {
        callback(value)
        completionHandler(KotlinUnit(), nil)
    }
}
