package me.gyanesh.shayariapp

import android.app.Application
import androidx.core.content.res.ResourcesCompat
import es.dmoral.toasty.Toasty
import me.gyanesh.shayariapp.data.FirebaseDataProvider
import me.gyanesh.shayariapp.data.db.ShayariDb
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.singleton
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance


class ShayariApp : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        mInstance = this

        configureToasty()
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@ShayariApp))
        bind() from singleton { ShayariDb.create(this@ShayariApp) }
        bind() from singleton { FirebaseDataProvider(instance()) }
//        bind() from singleton { NetworkConnectionInterceptor(applicationContext) }

//        bind() from provider { DiscussionRepository(instance()) }
    }

    private fun configureToasty() {
        ResourcesCompat.getFont(this, R.font.nunito)?.let {
            Toasty.Config.getInstance()
                .setToastTypeface(it)
                .allowQueue(false) // optional (prevents several Toastys from queuing)
                .apply()
        } // required
    }

    companion object {
        private lateinit var mInstance: ShayariApp

        @Synchronized
        fun getInstance(): ShayariApp {
            return mInstance
        }
    }

}