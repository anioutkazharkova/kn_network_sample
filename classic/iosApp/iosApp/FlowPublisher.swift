//
//  FlowPublisher.swift
//  iosApp
//
//  Created by Anna Zharkova on 03.01.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Combine
import shared


public struct FlowPublisher<T: AnyObject>: Publisher {
    public typealias Output = T
    public typealias Failure = Never
    
    private let wrapper: AnyFlow<Output>
    public init(wrapper: AnyFlow<Output>) {
        self.wrapper = wrapper
    }

    public func receive<S: Subscriber>(subscriber: S) where S.Input == Output, S.Failure == Failure {
        let subscription = FlowSubscription(wrapper: wrapper, subscriber: subscriber)
        subscriber.receive(subscription: subscription)
    }
    
    final class FlowSubscription<S: Subscriber>: Subscription where S.Input == Output, S.Failure == Failure {
        private var subscriber: S?
        private var job: shared.Cancellable? = nil

        private let wrapper: AnyFlow<Output>

        init(wrapper: AnyFlow<Output>, subscriber: S) {
            self.wrapper = wrapper
            self.subscriber = subscriber
          
            job = wrapper.collect(onEach: { data in
                subscriber.receive(data!)
            }, onCompletion: { error in
                if let error = error {
                    debugPrint(error.description())
                }
                subscriber.receive(completion: .finished)
            })
        }
      
        func cancel() {
            subscriber = nil
            job?.cancel()
        }

        func request(_ demand: Subscribers.Demand) {}
    }
}
