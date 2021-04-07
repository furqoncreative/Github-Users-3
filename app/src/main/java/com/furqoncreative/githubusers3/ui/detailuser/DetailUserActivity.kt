package com.furqoncreative.githubusers3.ui.detailuser

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.furqoncreative.githubusers3.R
import com.furqoncreative.githubusers3.databinding.ActivityDetailUserBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val username = intent.getStringExtra(ITEM)
        setupData(username)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(tabTitles[position])
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()

        (binding.tabMenu.layoutParams as CoordinatorLayout.LayoutParams).behavior =
            AppBarLayout.ScrollingViewBehavior()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getUser().observe(this, { item ->
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
                    val items = it.data
                    if (items != null) {
                        viewModel.setUser(items)
                    }
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    inner class SectionsPagerAdapter(
        fa: FragmentActivity,
        private val username: String?
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