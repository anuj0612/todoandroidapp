package app.todo.todoapplicatin.network

import retrofit2.Response
import retrofit2.http.GET

interface TodoApis {

    @GET("todos")
    suspend fun fetchTodoList(): Response<List<Todo>>
}