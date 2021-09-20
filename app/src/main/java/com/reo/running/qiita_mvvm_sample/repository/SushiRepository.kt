package com.reo.running.qiita_mvvm_sample.repository

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class SushiRepository @Inject constructor(
    private val context: Context
) {

    suspend fun getSushiList(): List<String> = withContext(Dispatchers.IO) {
        val resources = context.resources
        val assetManager = resources.assets //アセット呼び出し
        val inputStream = assetManager.open("sushi.json") //Jsonファイル
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))

        val gson = Gson()
        gson.fromJson(bufferedReader, Tmp::class.java).data
    }
}

class Tmp(
    val data: List<String>
)