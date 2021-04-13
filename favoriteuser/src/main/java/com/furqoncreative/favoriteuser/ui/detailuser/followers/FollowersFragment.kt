package com.furqoncreative.favoriteuser.ui.detailuser.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.furqoncreative.favoriteuser.databinding.FollowersFragmentBinding
import com.furqoncreative.favoriteuser.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowersFragment : Fragment() {

    companion object {
        fun newInstance(username: String?) = FollowersFragment().apply {
            arguments = Bundle(1).apply {
                putString("USERNAME", username)
            }
        }
    }

    private lateinit var binding: FollowersFragmentBinding
    private val viewModel: FollowersViewModel by viewModels()
    private lateinit var adapter: FollowersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FollowersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getFollowers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupData(arguments?.getString("USERNAME"))
    }

    private fun setupRecyclerView() {
        adapter = FollowersAdapter()
        binding.rvFollowers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowers.adapter = adapter
    }

    private fun setupData(username: String?) {
        viewModel.followers(username).observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    val items = it.data
                    if (!items.isNullOrEmpty()) {
                        viewModel.setFollowers(ArrayList(items))
                    }
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }


    private fun getFollowers() {
        viewModel.getFollowers().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setItems(it)
            }
        })
    }

}