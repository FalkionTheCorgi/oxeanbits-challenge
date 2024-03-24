package com.example.oxeanbits_challenge.koin_modules

import com.example.oxeanbits_challenge.network_monitor.NetworkMonitor
import io.mockk.mockk
import org.koin.dsl.module

val mockGeneralModule = module {
    single {
        mockk<NetworkMonitor>(relaxed = true)
    }
}
