package com.reo.running.qiita_mvvm_sample.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.reo.running.qiita_mvvm_sample.databinding.FragmentOrderBinding
import com.reo.running.qiita_mvvm_sample.viewModel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment() {

    private val viewModel: OrderViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentOrderBinding.inflate(inflater, container, false).let {
        it.lifecycleOwner = viewLifecycleOwner
        it.orderButton.setOnClickListener { viewModel.orderTuna() }
        it.billButton.setOnClickListener { viewModel.pay() }


        val orderImageObserver = Observer<Int> { newImageId ->
            it.orderImageView.setImageResource(newImageId)
        }


        val textObserver = Observer<String> { newText ->
            it.orderButton.text = newText
        }

        val cashObserver = Observer<String> { newText ->
            it.cashDisplay.text = newText
        }

        val billObserver = Observer<String> { newText ->
            it.billButton.text = newText
        }

        viewModel.orderImage.observe(viewLifecycleOwner, orderImageObserver)
        viewModel.orderText.observe(viewLifecycleOwner, textObserver)
        viewModel.cashDisplay.observe(viewLifecycleOwner, cashObserver)
        viewModel.billText.observe(viewLifecycleOwner, billObserver)
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.sushiList.observe(viewLifecycleOwner) {
            it.forEach {
                Log.d("debug", "sushi $it")
            }
        }
    }
}