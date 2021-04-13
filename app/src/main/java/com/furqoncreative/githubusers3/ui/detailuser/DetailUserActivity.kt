package com.furqoncreative.githubusers3.ui.detailuser

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.furqoncreative.githubusers3.R
import com.furqoncreative.githubusers3.data.entities.userdata.UserData
import com.furqoncreative.githubusers3.databinding.ActivityDetailUserBinding
import com.furqoncreative.githubusers3.helper.MappingHelper
import com.furqoncreative.githubusers3.ui.detailuser.followers.FollowersFragment
import com.furqoncreative.githubusers3.ui.detailuser.following.FollowingFragment
import com.furqoncreative.githubusers3.utils.Resource
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailUserActivity : AppCompatActivity() {
    companion object {
        const val ITEM = "ITEM"
    }

    private val tabTitles = arrayOf(
        R.string.text_followers,
        R.string.text_following
    )

    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel: DetailUserViewModel by viewModels()
    private var statusFavorite: Boolean = false
    private var userData: UserData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val userData = intent.getParcelableExtra<UserData>(ITEM)
        if (userData != null) {
            setupData(userData.login)
            userData.id?.let { checkUser(it) }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, userData?.login)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(tabTitles[position])
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()

        (binding.tabMenu.layoutParams as CoordinatorLayout.LayoutParams).behavior =
            AppBarLayout.ScrollingViewBehavior()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        val favorite: MenuItem? = menu?.findItem(R.id.action_favorite)
        if (favorite != null) {
            if (statusFavorite) {
                favorite.icon = ContextCompat.getDrawable(this, R.drawable.ic_action_favorite)
            } else {
                favorite.icon = ContextCompat.getDrawable(this, R.drawable.ic_action_unfavorite)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            if (statusFavorite) {
                item.icon = ContextCompat.getDrawable(this, R.drawable.ic_action_unfavorite)
                userData?.let { setUnfavorite(it) }
            } else {
                item.icon = ContextCompat.getDrawable(this, R.drawable.ic_action_favorite)
                userData?.let { setFavorite(it) }
            }

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getDetailUser().observe(this, { item ->
            if (item != null) {
                binding.txtUsername.text = StringBuilder("@${item.login}")
                binding.txtName.text = item.name
                binding.txtCompany.text = item.company
                binding.txtLocation.text = item.location
                binding.txtFollowerValue.text = item.followers.toString()
                binding.txtFollowingValue.text = item.following.toString()
                binding.txtRepositoryValue.text = item.publicRepos.toString()
                Glide.with(this).load(item.avatarUrl).circleCrop().into(binding.imgAvatar)
            }
        })
    }

    private fun setupData(username: String?) {
        viewModel.user(username).observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    userData = it.data
                    userData?.let { it1 -> viewModel.setDetailUser(it1) }
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    private fun checkUser(id: Int): Boolean {
        viewModel.setFavoriteById(id)

        viewModel.getFavoriteById().observe(this, {
            Log.d("USER", "DETAIL USER ${it?.count}")
            statusFavorite = it != null && it.count != 0
        })

        Log.d("USER", "STATUS USER $statusFavorite")
        return statusFavorite
    }

    private fun setFavorite(userData: UserData) {
        viewModel.addToFavoriteUser(MappingHelper.convertToContentValues(userData))
        statusFavorite = true
    }

    private fun setUnfavorite(userData: UserData) {
        userData.id?.let { viewModel.deleteFavoriteUser(it) }
        statusFavorite = false

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    inner class SectionsPagerAdapter(
        fa: FragmentActivity,
        private val username: String?,
    ) : FragmentStateAdapter(fa) {

        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> FollowersFragment.newInstance(username)
                1 -> FollowingFragment.newInstance(username)
                else -> FollowersFragment.newInstance(username)
            }
        }

    }
}