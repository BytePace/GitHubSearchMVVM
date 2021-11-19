package com.drus.githubsearch.mvvm.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.drus.githubsearch.core.extensions.hideKeyboard
import com.drus.githubsearch.core.navigation.Navigator
import com.drus.githubsearch.core.utils.ViewModelFactory
import com.drus.githubsearch.mvvm.R
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    private val navigator by lazy {
        Navigator(this, R.id.fragment_container) {
            hideKeyboard(findViewById(R.id.fragment_container))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        viewModel.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        viewModel.detach()
    }
}