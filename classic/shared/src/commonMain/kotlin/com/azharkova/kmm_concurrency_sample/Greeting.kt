package com.azharkova.kmm_concurrency_sample

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }

    fun greetingFlow(): CFlow<String> {
        return CFlow(flow {
            (1..10).forEach {
                println("about to emit $it")
                emit(it.toString())
                println("just emitted $it")
                delay(1000)
            }
        })//.flowOn(Dispatchers.Main))
    }
val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    fun greetingF() {
        val flow = flowOf(1, 2).onEach { delay(10) }
        val flow2 = flowOf("a", "b", "c").onEach { delay(15) }
            val f = CombineFlowData(flow, flow2) { arg1, arg2 ->
                print("do: $arg1 $arg2")
                arg1
            }.value

      scope.launch {
          f.collect {
              print("some " + it)
          }
      }
    }
}

