package app.todo.todoapplicatin.base

import android.app.Application
import app.todo.todoapplicatin.network.RestClient
import app.todo.todoapplicatin.repositories.Repository
import app.todo.todoapplicatin.viewmodel.MainActivityViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class App : Application(), KodeinAware {

    companion object {
        lateinit var appInstance: App
        var APP_BASE_URL = "https://b0cf-2405-201-f-4039-2177-1d2c-a577-329c.ngrok.io/"
        var APP_DEBUGGABLE = false
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))

        bind() from singleton { RestClient() }
        //Repositories
        bind() from singleton { Repository(instance()) }

        bind() from provider { MainActivityViewModel.MainActivityViewModelFactory(instance()) }
    }

}