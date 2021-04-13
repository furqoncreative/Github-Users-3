package com.furqoncreative.githubusers3.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furqoncreative.githubusers3.data.entities.searchuser.GithubUsersResponse
import com.furqoncreative.githubusers3.data.entities.userdata.UserData
import com.furqoncreative.githubusers3.data.repository.AppRepository
import com.furqoncreative.githubusers3.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {
    private val listUsers = MutableLiveData<ArrayList<UserData>>()

    val users: (String?) -> LiveData<Resource<GithubUsersResponse>> =
        { value -> repository.getSearchUsers(value) }

    fun setListUsers(list: ArrayList<UserData>) {
        listUsers.value = list
    }

    fun getListUsers(): MutableLiveData<ArrayList<UserData>> {
        return listUsers
    }
}
