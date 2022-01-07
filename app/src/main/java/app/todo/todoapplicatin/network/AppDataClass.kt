package app.todo.todoapplicatin.network

data class AppDataClass(
    val id: Int
)

data class MyResponse<T>(
    var success: Boolean = false,
    var msg: String? = null,
    var data: T
)

data class Todo(
    val id:Int,
    val title:String,
    val done:Boolean
)
