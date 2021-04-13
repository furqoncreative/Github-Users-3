package com.furqoncreative.githubusers3.ui.favoriteuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furqoncreative.githubusers3.data.entities.userdata.UserData
import com.furqoncreative.githubusers3.data.repository.AppRepository
import com.furqoncreative.githubusers3.helper.MappingHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteUserViewModel @Inject constructor(
    val repository: AppRepository
) : ViewModel() {
    private val favoriteList = MutableLiveData<ArrayList<UserData>>()

    fun setFavoriteList() {
        val cursorFavorite = repository.getAllFavorite()
        val list = MappingHelper.mapCursorToArrayLits(cursorFavorite)
        favoriteList.postValue(list)
    }

    fun getFavoriteList(): LiveData<ArrayList<UserData>> = favoriteList

}
