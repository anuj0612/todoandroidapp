package app.todo.todoapplicatin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.todo.todoapplicatin.network.Todo

class TodoAdapter(private val todoList: List<Todo>) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvText = itemView.findViewById(R.id.tvText) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.todo_cell, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todoObject = todoList[position]
        holder.tvText.text = todoObject.title
    }

    override fun getItemCount() = todoList.size
}