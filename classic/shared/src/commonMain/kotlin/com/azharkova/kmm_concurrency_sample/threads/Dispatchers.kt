package com.azharkova.kmm_concurrency_sample

import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

expect val ioDispatcher: CoroutineContext

expect val uiDispatcher: CoroutineContext
