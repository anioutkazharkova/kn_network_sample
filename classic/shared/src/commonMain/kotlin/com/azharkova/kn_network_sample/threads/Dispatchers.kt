package com.azharkova.kn_network_sample.threads

import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

expect val ioDispatcher: CoroutineContext

expect val uiDispatcher: CoroutineContext
