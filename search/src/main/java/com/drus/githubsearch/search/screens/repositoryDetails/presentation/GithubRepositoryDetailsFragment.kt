package com.drus.githubsearch.search.screens.repositoryDetails.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.drus.githubsearch.core.utils.SaveClickListener
import com.drus.githubsearch.core.utils.ViewModelFactory
import com.drus.githubsearch.search.R
import com.drus.githubsearch.search.databinding.FragmentRepositoryDetailsBinding
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo
import javax.inject.Inject

class GithubRepositoryDetailsFragment : Fragment(R.layout.fragment_repository_details) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<GithubRepositoryDetailsViewModel> { viewModelFactory }
    private var _binding: FragmentRepositoryDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireArguments().getParcelable<SimpleRepositoryInfo>(INFO)?.let {
            viewModel.startInit(it)
        }
        binding.title.apply {
            setOnClickListener(SaveClickListener {
                viewModel.back()
            }
            )
        }
        binding.date.apply {
            setOnClickListener(SaveClickListener {
                viewModel.back()
            })
            viewModel.date.observe(viewLifecycleOwner) {
                text = it
//                isVisible = it != null && it.isNotEmpty()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "RepositoryDetailsFragment"
        const val INFO = "$TAG:info"

        fun newInstance(
            info: SimpleRepositoryInfo
        ) = GithubRepositoryDetailsFragment().apply {
            arguments = bundleOf(
                INFO to info
            )
        }
    }
}