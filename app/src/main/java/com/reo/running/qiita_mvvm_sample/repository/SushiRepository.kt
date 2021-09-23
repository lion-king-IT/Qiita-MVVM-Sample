package com.reo.running.qiita_mvvm_sample.repository

import android.content.Context
import com.google.gson.Gson
import com.reo.running.qiita_mvvm_sample.model.Sushi
import com.reo.running.qiita_mvvm_sample.model.SushiDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class SushiRepository @Inject constructor(
    private val context: Context,
    private val sushiDao: SushiDao
) {
    suspend fun getSushiList(): List<String> = withContext(Dispatchers.IO) {
        val resources = context.resources
        val assetManager = resources.assets
        val inputStream = assetManager.open("sushi.json")
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))

        val gson = Gson()
        gson.fromJson(bufferedReader, Tmp::class.java).data
    }

    suspend fun getAllSushiData() = sushiDao.getAll()

    suspend fun saveSushiData(sushiData: Sushi) = withContext(Dispatchers.IO) {
        sushiDao.insertSushi(sushiData)
    }
}

class Tmp(
    val data: List<String>
)
