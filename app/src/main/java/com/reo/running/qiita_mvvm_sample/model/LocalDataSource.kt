package com.reo.running.qiita_mvvm_sample.model

import androidx.room.*

@Database(entities = arrayOf(Sushi::class), version = 1,exportSchema = false)
abstract class SushiDatabase : RoomDatabase() {
    abstract fun suhiDao(): SushiDao
}

@Entity
data class Sushi(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val orderHistory: String,
    val price: String
)

@Dao
interface SushiDao {
    @Query("SELECT * FROM sushi")
    fun getAll(): List<Sushi>

    @Query("SELECT * FROM sushi where id = :id")
    fun getHistory(id: Int): Sushi

    @Insert
    fun insertSushi(sushi: Sushi)

    @Delete
    fun delete(sushi: Sushi)
}