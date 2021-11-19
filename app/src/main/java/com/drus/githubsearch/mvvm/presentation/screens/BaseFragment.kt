package com.drus.githubsearch.mvvm.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.drus.githubsearch.mvvm.BR
import dagger.android.support.DaggerFragment

abstract class BaseFragment<VM : ViewModel, B : ViewDataBinding>(
    @LayoutRes private val layoutId: Int,
    private val viewModelID: Int? = BR.viewModel
) : DaggerFragment() {
    protected lateinit var binding: B
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        configureBinding()
        return binding.root
    }

    @CallSuper
    protected open fun configureBinding() {
        viewModelID?.let {
            binding.setVariable(it, viewModel)
        }
    }
}