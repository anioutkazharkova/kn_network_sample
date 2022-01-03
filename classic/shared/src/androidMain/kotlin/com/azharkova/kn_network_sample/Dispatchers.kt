package com.azharkova.kn_network_sample.threads

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

actual val uiDispatcher: CoroutineContext = Dispatchers.Main
actual val ioDispatcher: CoroutineContext = Dispatchers.IO