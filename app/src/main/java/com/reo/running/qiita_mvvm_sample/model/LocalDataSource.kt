package com.reo.running.qiita_mvvm_sample.model

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import com.reo.running.qiita_mvvm_sample.repository.SushiDao

@Database(entities = arrayOf(Sushi::class), version = 1)
abstract class SushiDatabase : RoomDatabase() {
    abstract fun suhiDao(): SushiDao
}

@Entity
data class Sushi(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val orderHistory: Int,
    val price: Int
)