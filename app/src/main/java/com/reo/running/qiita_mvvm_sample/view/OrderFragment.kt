package com.reo.running.qiita_mvvm_sample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.reo.running.qiita_mvvm_sample.databinding.FragmentOrderBinding
import com.reo.running.qiita_mvvm_sample.viewModel.OrderViewModel

class OrderFragment : Fragment() {

    // 客席にタッチパネル設置
    private val viewModel: OrderViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentOrderBinding.inflate(inflater, container, false).let {
        it.lifecycleOwner = viewLifecycleOwner
        it.orderViewModel = viewModel

        // 監視センサ作成
        val imageObserver = Observer<Int> { newImageId ->
            // ImageViewの更新処理
            it.orderImageView.setImageResource(newImageId)
        }

        // 監視センサ作成
        val textObserver = Observer<String> { newText ->
            // TextViewの更新処理
            it.tunaOrderButton.text = newText
        }

        // viewLifecycleOwnerを第一引数年て渡すことで、お客さんに合わせて監視する設定
        viewModel.orderImage.observe(viewLifecycleOwner, imageObserver)
        viewModel.orderText.observe(viewLifecycleOwner, textObserver)
        it.root
    }
}