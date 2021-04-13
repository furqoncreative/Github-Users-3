package com.furqoncreative.favoriteuser.ui.detailuser.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.furqoncreative.favoriteuser.databinding.FollowingFragmentBinding
import com.furqoncreative.favoriteuser.utils.Resource.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingFragment : Fragment() {

    companion object {
        fun newInstance(username: String?) = FollowingFragment().apply {
            arguments = Bundle(1).apply {
                putString("USERNAME", username)
            }
        }
    }

    private lateinit var binding: FollowingFragmentBinding
    private val viewModel: FollowingViewModel by viewModels()
    private lateinit var adapter: FollowingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FollowingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getFollowing()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupData(arguments?.getString("USERNAME"))
    }

    private fun setupRecyclerView() {
        adapter = FollowingAdapter()
        binding.rvFollowing.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowing.adapter = adapter
    }

    private fun setupData(username: String?) {
        viewModel.following(username).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    val items = it.data
                    if (!items.isNullOrEmpty()) {
                        viewModel.setFollowing(ArrayList(items))
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }


    private fun getFollowing() {
        viewModel.getFollowing().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setItems(it)
            }
        })
    }
}