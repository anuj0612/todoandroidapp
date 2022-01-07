package app.todo.todoapplicatin.network

import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object Coroutines {

    //Use for doing small task.. fetch from network and switch to this context to update ui
    fun main(work: suspend (() -> Unit)) = CoroutineScope(Dispatchers.Main).launch(handler) {
        work()
    }

    //Only use for Background Task
    //Note: Cannot access UI with this Dispatcher
    fun io(work: suspend (() -> Unit)) = CoroutineScope(Dispatchers.IO).launch(handler) {
        work()
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        Logger.d("Error has occurred $exception")
    }

}