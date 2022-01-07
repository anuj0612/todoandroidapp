package app.todo.todoapplicatin.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

open class BaseViewModel<VIEW_STATE>(initialValue: VIEW_STATE) : ViewModel() {

    val viewState = NonNullMutableLiveData(initialValue)

    fun updateViewState(update: (VIEW_STATE) -> VIEW_STATE) {
        viewState.value = update(viewState.value)
    }

}

open class NonNullMutableLiveData<T>(initialValue: T) : MutableLiveData<T>() {
    init {
        value = initialValue
    }

    //Not allowing Null Values
    override fun getValue(): T {
        return super.getValue()!!
    }

    fun observe(owner: LifecycleOwner, block: (T) -> Unit) {
        observe(owner, Observer {
            it?.let(block)
        })
    }
}