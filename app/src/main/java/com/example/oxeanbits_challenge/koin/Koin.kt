package com.example.oxeanbits_challenge.koin

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.startKoin

class Koin : Application() {

    override fun onCreate() {
        super.onCreate()

        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidLogger()
                androidContext(this@Koin)
                modules(
                    listOf(
                        networkModule,
                        databaseModule,
                        generalModule,
                        viewModelModule
                    )
                )
            }
        }
    }
}