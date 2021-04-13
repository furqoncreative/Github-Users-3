package com.furqoncreative.favoriteuser.ui.detailuser.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furqoncreative.favoriteuser.data.entities.followings.FollowingsItem
import com.furqoncreative.favoriteuser.data.repository.AppRepository
import com.furqoncreative.favoriteuser.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {
    private val followingList = MutableLiveData<ArrayList<FollowingsItem>>()

    val following: (String?) -> LiveData<Resource<List<FollowingsItem>>> =
        { value -> repository.getFollowingUsers(value) }

    fun setFollowing(following: ArrayList<FollowingsItem>) {
        followingList.value = following
    }

    fun getFollowing(): MutableLiveData<ArrayList<FollowingsItem>> {
        return followingList
    }
}