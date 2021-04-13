package com.furqoncreative.githubusers3.ui.favoriteuser

import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.furqoncreative.githubusers3.R
import com.furqoncreative.githubusers3.data.entities.userdata.UserData
import com.furqoncreative.githubusers3.data.provider.FavoriteProvider.Companion.CONTENT_URI
import com.furqoncreative.githubusers3.databinding.ActivityFavoriteUserBinding
import com.furqoncreative.githubusers3.ui.detailuser.DetailUserActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteUserActivity : AppCompatActivity(), FavoriteUserAdapter.GithubUserItemListener {

    private lateinit var binding: ActivityFavoriteUserBinding
    private val viewModel: FavoriteUserViewModel by viewModels()
    private lateinit var adapter: FavoriteUserAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setupRecyclerView()
        setupData(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.items)
    }

    private fun setupRecyclerView() {
        adapter = FavoriteUserAdapter(this)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter
    }


    private fun setupData(savedInstanceState: Bundle?) {
        val handler = Handler(Looper.getMainLooper())

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                getFavoriteList()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            getFavoriteList()
        } else {
            val list = savedInstanceState.getParcelableArrayList<UserData>(EXTRA_STATE)
            if (list != null) {
                adapter.setItems(list)
            }
        }
    }

    private fun getFavoriteList() {
        viewModel.setFavoriteList()

        viewModel.getFavoriteList().observe(this, {
            if (!it.isNullOrEmpty()) {
                adapter.setItems(ArrayList(it))
                binding.rvUsers.visibility = View.VISIBLE
                binding.layoutSearchResult.root.visibility = View.GONE
            } else {
                binding.rvUsers.visibility = View.GONE
                binding.layoutSearchResult.root.visibility = View.VISIBLE
                binding.layoutSearchResult.textResult.text =
                    resources.getString(R.string.text_no_favorite)
            }
        })
    }

    override fun onClickedUser(userData: UserData) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.ITEM, userData)
        startActivity(intent)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}