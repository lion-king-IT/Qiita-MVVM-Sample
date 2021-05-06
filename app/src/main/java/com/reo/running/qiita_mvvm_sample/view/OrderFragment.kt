package com.reo.running.qiita_mvvm_sample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.reo.running.qiita_mvvm_sample.databinding.FragmentOrderBinding
import com.reo.running.qiita_mvvm_sample.viewModel.OrderViewModel

class OrderFragment : Fragment() {
    private val viewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentOrderBinding.inflate(inflater, container, false).let {
        it.lifecycleOwner = viewLifecycleOwner
        it.orderViewModel = viewModel
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orderObserver = Observer<String> { _ ->
        }
    }
}