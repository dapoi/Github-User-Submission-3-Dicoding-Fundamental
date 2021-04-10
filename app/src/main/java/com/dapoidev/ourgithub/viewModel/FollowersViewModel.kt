package com.dapoidev.ourgithub.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dapoidev.ourgithub.model.UserModel
import com.dapoidev.ourgithub.model.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<UserModel>>()

    fun setListFollowers(username: String) {
        ApiClient.apiReq
            .getUserFollowers(username)
            .enqueue(object : Callback<ArrayList<UserModel>>{
                override fun onResponse(
                    call: Call<ArrayList<UserModel>>,
                    response: Response<ArrayList<UserModel>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserModel>>, error: Throwable) {
                    Log.d("Failure", error.message.toString())
                }
            })
    }

    fun getListFollowers(): LiveData<ArrayList<UserModel>> = listFollowers
}