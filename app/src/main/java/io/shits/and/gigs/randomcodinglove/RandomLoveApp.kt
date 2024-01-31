package io.shits.and.gigs.randomcodinglove

import android.app.Application
import io.shits.and.gigs.randomcodinglove.di.loveModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RandomLoveApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }


    private fun initKoin(){
        startKoin {
            androidLogger()
            androidContext(this@RandomLoveApp)
            modules(loveModule)
        }
    }

}