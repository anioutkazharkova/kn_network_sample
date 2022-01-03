//
//  CustomError.swift
//  iosApp
//
//  Created by Anna Zharkova on 03.01.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class CustomError :Error {
    var message: String = ""
    init(error: KotlinThrowable?) {
        message = error?.description() ?? "error"
    }
    
    init(error: String) {
        message = error
    }
}
