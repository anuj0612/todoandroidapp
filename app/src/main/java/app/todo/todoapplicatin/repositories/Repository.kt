package app.todo.todoapplicatin.repositories

import app.todo.todoapplicatin.network.RestClient
import app.todo.todoapplicatin.network.SafeApiRequest
import app.todo.todoapplicatin.network.Todo

class Repository(private val networkClient: RestClient) : SafeApiRequest() {

    suspend fun fetchTodo(): List<Todo> {
        return makeRequest {
            networkClient.getServiceApi().fetchTodoList()
        }
    }
}