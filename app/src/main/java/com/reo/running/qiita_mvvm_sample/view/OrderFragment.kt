package com.reo.running.qiita_mvvm_sample.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.reo.running.qiita_mvvm_sample.R
import com.reo.running.qiita_mvvm_sample.databinding.FragmentOrderBinding
import com.reo.running.qiita_mvvm_sample.viewModel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment(R.layout.fragment_order) {

    private val viewModel: OrderViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FragmentOrderBinding.bind(view).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = viewModel
        }
        viewModel.sushiList.observe(viewLifecycleOwner) { it ->
            it.forEach {
                Log.d("debug", it)
            }
        }
    }
}
