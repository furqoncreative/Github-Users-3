package com.furqoncreative.githubusers3.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.furqoncreative.githubusers3.R
import com.furqoncreative.githubusers3.databinding.ActivityMainBinding
import com.furqoncreative.githubusers3.ui.detailuser.DetailUserActivity
import com.furqoncreative.githubusers3.ui.main.adapter.SearchUserAdapter
import com.furqoncreative.githubusers3.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchUserAdapter.GithubUSerItemListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: SearchUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView: SearchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                doSearchUser(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_language) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        adapter = SearchUserAdapter(this)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter
    }

    private fun doSearchUser(username: String?) {
        viewModel.users(username).observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = GONE
                    val items = it.data?.items
                    viewModel.setListUsers(ArrayList(items))
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING ->
                    binding.progressBar.visibility = VISIBLE
            }
        })
    }


    private fun setupData() {
        viewModel.getListUsers().observe(this, {
            if (!it.isNullOrEmpty()) {
                adapter.setItems(it)
                binding.rvUsers.visibility = VISIBLE
                binding.layoutSearchResult.root.visibility = GONE
            } else {
                binding.rvUsers.visibility = GONE
                binding.layoutSearchResult.root.visibility = VISIBLE
                binding.layoutSearchResult.textResult.text =
                    resources.getString(R.string.text_not_found)
            }
        })
    }

    override fun onClickedUser(username: String?) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.ITEM, username)
        startActivity(intent)
    }
}