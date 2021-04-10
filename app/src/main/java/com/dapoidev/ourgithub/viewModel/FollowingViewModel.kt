package com.dapoidev.ourgithub.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dapoidev.ourgithub.model.UserModel
import com.dapoidev.ourgithub.model.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class FollowingViewModel : ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<UserModel>>()

    fun setListFollowing(username: String) {
        ApiClient.apiReq
            .getUserFollowing(username)
            .enqueue(object : Callback<ArrayList<UserModel>> {
                override fun onResponse(
                    call: Call<ArrayList<UserModel>>,
                    response: Response<ArrayList<UserModel>>
                ) {
                    if (response.isSuccessful) {
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserModel>>, error: Throwable) {
                    Log.d("Failure", error.message.toString())
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<UserModel>> = listFollowing
}