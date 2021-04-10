package com.dapoidev.ourgithub.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dapoidev.ourgithub.model.UserModel
import com.dapoidev.ourgithub.model.UserArray
import com.dapoidev.ourgithub.model.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    val listUser = MutableLiveData<ArrayList<UserModel>>()

    fun setUserSearch(query: String) {
        ApiClient.apiReq.getUserSearch(query).enqueue(object : Callback<UserArray> {
            override fun onResponse(call: Call<UserArray>, response: Response<UserArray>) {
                if (response.isSuccessful) {
                    listUser.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<UserArray>, error: Throwable) {
                Log.d("Failure", error.message.toString())
            }

        })
    }

    fun getSearchUser(): LiveData<ArrayList<UserModel>> = listUser
}