package com.furqoncreative.favoriteuser.ui.favoriteuser

import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.furqoncreative.favoriteuser.R
import com.furqoncreative.favoriteuser.data.entities.userdata.UserData
import com.furqoncreative.favoriteuser.data.local.ContentUri.Companion.CONTENT_URI
import com.furqoncreative.favoriteuser.databinding.ActivityFavoriteUserBinding
import com.furqoncreative.favoriteuser.ui.detailuser.DetailUserActivity
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
                binding.layoutResult.root.visibility = View.GONE
            } else {
                binding.rvUsers.visibility = View.GONE
                binding.layoutResult.root.visibility = View.VISIBLE
                binding.layoutResult.textResult.text =
                    resources.getString(R.string.text_no_favorite)
            }
        })
    }

    override fun onClickedUser(userData: UserData) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.ITEM, userData)
        startActivity(intent)
    }

}