package com.vienotes

import android.app.Application
import com.vienotes.di.homeModule
import com.vienotes.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VienotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)

            modules(
                listOf(
                    homeModule,
                    networkModule
                )
            )
        }
    }
}