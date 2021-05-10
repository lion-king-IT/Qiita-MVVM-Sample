package com.reo.running.qiita_mvvm_sample

import android.app.Application
import androidx.room.Room
import com.reo.running.qiita_mvvm_sample.model.SushiDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    companion object {
        lateinit var db: SushiDatabase
    }

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            SushiDatabase::class.java,
            "database"
        ).build()

    }
}