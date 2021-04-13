package com.furqoncreative.githubusers3.ui.detailuser

import android.content.ContentValues
import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furqoncreative.githubusers3.data.entities.userdata.UserData
import com.furqoncreative.githubusers3.data.repository.AppRepository
import com.furqoncreative.githubusers3.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {
    private var userData = MutableLiveData<UserData?>()
    private val favoriteUser = MutableLiveData<Cursor?>()

    //remote
    val user: (String?) -> LiveData<Resource<UserData>> =
        { value -> repository.getUser(value) }

    fun setDetailUser(value: UserData) = value.also { userData.value = it }

    fun getDetailUser(): MutableLiveData<UserData?> = userData

    //local
    fun setFavoriteById(id: Int) {
        val cursorFavorite = repository.getFavoriteById(id)
        favoriteUser.postValue(cursorFavorite)
    }

    fun addToFavoriteUser(favorite: ContentValues) {
        repository.setFavorite(favorite)
    }

    fun deleteFavoriteUser(id: Int) {
        repository.deleteFavorite(id)
    }

    fun getFavoriteById(): MutableLiveData<Cursor?> = favoriteUser

}