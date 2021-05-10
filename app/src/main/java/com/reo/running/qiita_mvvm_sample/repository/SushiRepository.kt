package com.reo.running.qiita_mvvm_sample.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.reo.running.qiita_mvvm_sample.model.Sushi
import javax.inject.Inject


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

class SushiRepository @Inject constructor(
    private val sushiDao: SushiDao
)