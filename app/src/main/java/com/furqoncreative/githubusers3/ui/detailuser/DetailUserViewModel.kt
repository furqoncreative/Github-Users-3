package com.furqoncreative.githubusers3.ui.detailuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furqoncreative.githubusers3.data.entities.detailuser.DetailUserResponse
import com.furqoncreative.githubusers3.data.repository.AppRepository
import com.furqoncreative.githubusers3.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {
    private val userData = MutableLiveData<DetailUserResponse>()

    val user: (String?) -> LiveData<Resource<DetailUserResponse>> =
        { value -> repository.getUser(value) }

    fun setUser(user: DetailUserResponse) {
        userData.value = user
    }

    fun getUser(): MutableLiveData<DetailUserResponse> {
        return userData
    }
}