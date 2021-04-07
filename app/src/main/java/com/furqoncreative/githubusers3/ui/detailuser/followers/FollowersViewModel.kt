package com.furqoncreative.githubusers3.ui.detailuser.followers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furqoncreative.githubusers3.data.entities.followers.FollowersItem
import com.furqoncreative.githubusers3.data.repository.AppRepository
import com.furqoncreative.githubusers3.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowersViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {
    private val followersList = MutableLiveData<ArrayList<FollowersItem>>()

    val followers: (String?) -> LiveData<Resource<List<FollowersItem>>> =
        { value -> repository.getFollowersUsers(value) }

    fun setFollowers(followers: ArrayList<FollowersItem>) {
        followersList.value = followers
    }

    fun getFollowers(): MutableLiveData<ArrayList<FollowersItem>> {
        return followersList
    }
}