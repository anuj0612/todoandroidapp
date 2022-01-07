package app.todo.todoapplicatin.base

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.todo.todoapplicatin.databinding.BaseLayoutBinding
import com.google.android.material.snackbar.Snackbar
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

open class BaseActivity : AppCompatActivity(), KodeinAware {
    private lateinit var baseBinding: BaseLayoutBinding
    lateinit var mContext: Context


    override val kodein: Kodein by kodein()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        baseBinding = BaseLayoutBinding.inflate(layoutInflater)
        setContentView(baseBinding.root)
        Hawk.init(mContext).build()
        Logger.addLogAdapter(AndroidLogAdapter())
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    fun View.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(this, message, duration).also {
            it.setAction("OK") { _ ->
                it.dismiss()
            }
            it.setActionTextColor(Color.WHITE)
        }.show()
    }

    fun Context.showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun Context.showLongToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
        val intent = Intent(this, it)
        intent.putExtras(Bundle().apply(extras))
        startActivity(intent)
    }

    fun <T> Context.clearTaskAndOpenActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
        val intent = Intent(this, it)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtras(Bundle().apply(extras))
        startActivity(intent)
    }

    fun Activity.hideKeyboard() {
        currentFocus?.let {
            (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                it.windowToken,
                0
            )
        }
    }

    fun showDialogBox(msg: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(mContext)
        builder.setTitle("Seafarer")
        builder.setMessage(msg)
        builder.setCancelable(true)

        builder.setPositiveButton(
            "Ok"
        ) { dialog, id -> dialog.cancel() }

        val alert: AlertDialog = builder.create()
        alert.show()
    }


}