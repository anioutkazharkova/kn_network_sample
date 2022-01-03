package com.azharkova.kmm_concurrency_sample

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

actual val ioDispatcher: CoroutineContext = Dispatchers.IO

actual val uiDispatcher: CoroutineContext = Dispatchers.Main