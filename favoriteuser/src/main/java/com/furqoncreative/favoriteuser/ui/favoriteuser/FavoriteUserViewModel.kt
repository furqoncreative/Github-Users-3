package com.furqoncreative.favoriteuser.ui.favoriteuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furqoncreative.favoriteuser.data.entities.userdata.UserData
import com.furqoncreative.favoriteuser.data.repository.AppRepository
import com.furqoncreative.favoriteuser.helper.MappingHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteUserViewModel @Inject constructor(
    val repository: AppRepository,
) : ViewModel() {
    private val favoriteList = MutableLiveData<ArrayList<UserData>>()

    fun setFavoriteList() {
        val cursorFavorite = repository.getAllFavorite()
        val list = MappingHelper.mapCursorToArrayLits(cursorFavorite)
        favoriteList.postValue(list)
    }

    fun getFavoriteList(): LiveData<ArrayList<UserData>> = favoriteList

}
