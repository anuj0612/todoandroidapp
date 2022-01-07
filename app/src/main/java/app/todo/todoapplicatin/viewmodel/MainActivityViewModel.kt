package app.todo.todoapplicatin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.todo.todoapplicatin.base.BaseViewModel
import app.todo.todoapplicatin.modelclass.MainActivityViewState
import app.todo.todoapplicatin.network.Coroutines
import app.todo.todoapplicatin.network.Todo
import app.todo.todoapplicatin.repositories.Repository
import com.orhanobut.logger.Logger

class MainActivityViewModel(private val mRepository: Repository) :
    BaseViewModel<MainActivityViewState>(initialValue = initialState) {

    @Suppress("UNCHECKED_CAST")
    class MainActivityViewModelFactory(
        private val repository: Repository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainActivityViewModel(repository) as T
        }
    }

    companion object {
        val initialState = MainActivityViewState(
            isLoading = false,
            todoList = emptyList()
        )
    }

    private val _messageCommunicatorViewState by lazy {
        MutableLiveData<String>()
    }

    val messageCommunicatorViewState: LiveData<String>
        get() = _messageCommunicatorViewState


    private val _dataCommunicatorViewState by lazy {
        MutableLiveData<List<Todo>>()
    }

    val dataCommunicatorViewState: LiveData<List<Todo>>
        get() = _dataCommunicatorViewState


    fun fetchTodo() {

        Coroutines.main {
            updateViewState { it.copy(isLoading = true) }
            try {
                val todoData = mRepository.fetchTodo()
                updateViewState { it.copy(todoList = todoData) }
                _dataCommunicatorViewState.value = todoData
            } catch (e: Throwable) {
                _messageCommunicatorViewState.value = e.message
            }
            updateViewState { it.copy(isLoading = false) }

        }
    }

}