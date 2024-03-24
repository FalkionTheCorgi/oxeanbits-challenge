package com.example.oxeanbits_challenge.koin

import android.content.Context
import androidx.room.Room
import com.example.oxeanbits_challenge.database.Database
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    factory {
        createRoomInstance(androidContext())
    }

}

private fun createRoomInstance(context: Context): Database {
    return Room.databaseBuilder(
        context,
        Database::class.java,
        "oxeanbits-challenge"
    ).build()
}