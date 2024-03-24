package com.example.oxeanbits_challenge.koin

import com.example.oxeanbits_challenge.network_monitor.NetworkMonitor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val generalModule = module {

    factory {
        NetworkMonitor(androidContext())
    }

}