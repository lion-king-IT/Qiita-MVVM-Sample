package com.reo.running.qiita_mvvm_sample.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.reo.running.qiita_mvvm_sample.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val orderFragment = OrderFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentContainer, orderFragment)
        transaction.commit()
    }
}