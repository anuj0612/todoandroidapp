package app.todo.todoapplicatin

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.kodein.di.generic.instance
import app.todo.todoapplicatin.base.BaseActivity
import app.todo.todoapplicatin.databinding.ActivityMainBinding
import app.todo.todoapplicatin.network.Todo
import app.todo.todoapplicatin.viewmodel.MainActivityViewModel
import com.google.gson.Gson
import com.orhanobut.logger.Logger


class MainActivity : BaseActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter
    private val factory: MainActivityViewModel.MainActivityViewModelFactory by instance()
    private val mHomeViewModel: MainActivityViewModel? by viewModels(factoryProducer = { factory })

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mHomeViewModel?.fetchTodo()

        mHomeViewModel?.dataCommunicatorViewState?.observe(this, Observer {
            mainBinding.rvTodoList.layoutManager = LinearLayoutManager(mContext)
            todoAdapter = TodoAdapter(it)
            mainBinding.rvTodoList.adapter = todoAdapter
            mainBinding.rvTodoList.adapter!!.notifyDataSetChanged()
        })

    }
}