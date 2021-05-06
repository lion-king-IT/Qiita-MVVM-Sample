package com.reo.running.qiita_mvvm_sample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reo.running.qiita_mvvm_sample.R
import com.reo.running.qiita_mvvm_sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.run {
            val orderFragment = OrderFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragmentContainer, orderFragment)
            transaction.commit()
        }
    }
}