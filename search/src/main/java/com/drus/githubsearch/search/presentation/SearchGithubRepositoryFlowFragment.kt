package com.drus.githubsearch.search.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.drus.githubsearch.search.R
import com.drus.githubsearch.search.databinding.FragmentSearchGithubRepositoryFlowBinding
import com.drus.githubsearch.search.di.DaggerSearchGithubRepositoryComponent
import com.drus.githubsearch.search.di.SearchGithubRepositoryComponent
import com.drus.githubsearch.search.di.SearchGithubRepositoryComponentProvider
import com.drus.githubsearch.search.di.SearchGithubRepositoryModule
import com.drus.githubsearch.search.screens.search.presentation.SearchGithubRepositoryFragment

class SearchGithubRepositoryFlowFragment: Fragment(), SearchGithubRepositoryComponentProvider {

    private lateinit var searchGithubRepositoryComponent: SearchGithubRepositoryComponent

    private var _binding: FragmentSearchGithubRepositoryFlowBinding? = null
    private val binding get() = _binding!!


    override fun onAttach(context: Context) {
        super.onAttach(context)
        searchGithubRepositoryComponent = DaggerSearchGithubRepositoryComponent.builder()
            .searchGithubRepositoryModule(SearchGithubRepositoryModule())
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchGithubRepositoryFlowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction
            .replace(R.id.container, SearchGithubRepositoryFragment(), tag)
            .addToBackStack(SearchGithubRepositoryFragment().javaClass.canonicalName)
            .setReorderingAllowed(true)
            .commit()
        childFragmentManager.executePendingTransactions()
    }

    override fun getSearchGithubRepositoryComponent(): SearchGithubRepositoryComponent {
        return searchGithubRepositoryComponent
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}