package com.furqoncreative.favoriteuser.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.furqoncreative.favoriteuser.utils.Resource.Status.ERROR
import com.furqoncreative.favoriteuser.utils.Resource.Status.SUCCESS
import kotlinx.coroutines.Dispatchers

fun <A> remoteOnlyOperation(
    networkCall: suspend () -> Resource<A>
): LiveData<Resource<A>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            emit(Resource.success(responseStatus.data!!))
        } else if (responseStatus.status == ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
    }


