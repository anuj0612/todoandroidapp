package app.todo.todoapplicatin.modelclass

import app.todo.todoapplicatin.network.Todo

data class MainActivityViewState(
    val isLoading: Boolean,
    val todoList: List<Todo>
)