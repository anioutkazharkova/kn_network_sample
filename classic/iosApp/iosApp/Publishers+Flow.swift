//
//  Publishers+Flow.swift
//  iosApp
//
//  Created by Anna Zharkova on 03.01.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Combine
import shared

extension Publishers {
    static func createPublisher<T>(
        wrapper: AnyFlow<T>
    ) -> AnyPublisher<T?, Error> {
        var job: shared.Cancellable? = nil  //Kotlinx_coroutines_coreJob? = nil
        return Deferred {
            Future { promise in
                job = wrapper.collect(onEach: { value in
                    promise(.success(value))
                }, onCompletion: { error in
                    promise(.failure(CustomError (error:error)))
                })
            }.handleEvents( receiveCancel:
                                {
                job?.cancel()
            })
        }.eraseToAnyPublisher()
    }
}



extension NewsService {
    
    func getNewsList()-> AnyPublisher<[NewsItem], Error> {
        return Deferred {
            Future { promise in
                self.getNewsList { response, error in
                    if let data = response?.content?.articles {
                        promise(.success(data))
                    }
                    if let error = response?.errorResponse {
                        promise(.failure(CustomError(error: error.message)))
                    }
                    if let error = error {
                        promise(.failure(error))
                    }
                }
            }
            
        }.eraseToAnyPublisher()
    }
}
