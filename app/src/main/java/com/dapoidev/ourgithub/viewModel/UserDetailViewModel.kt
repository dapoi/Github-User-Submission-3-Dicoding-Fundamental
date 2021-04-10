package com.dapoidev.ourgithub.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dapoidev.ourgithub.data.UserFav
import com.dapoidev.ourgithub.data.UserFavDao
import com.dapoidev.ourgithub.data.database.UserFavDatabase
import com.dapoidev.ourgithub.model.UserDetail
import com.dapoidev.ourgithub.model.retrofit.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class UserDetailViewModel(app: Application) : AndroidViewModel(app) {
    val userDetail = MutableLiveData<UserDetail>()

    private var userFavDao: UserFavDao?
    private var userFavDB: UserFavDatabase? = UserFavDatabase.getDB(app)

    init {
        userFavDao = userFavDB?.userFavDao()
    }

    fun setUserDetail(username: String) {
        ApiClient.apiReq
            .getUserDetail(username)
            .enqueue(object : retrofit2.Callback<UserDetail> {
                override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                    if (response.isSuccessful) {
                        userDetail.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetail>, error: Throwable) {
                    Log.d("Failure", error.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<UserDetail> = userDetail

    // menyiapkan data agar dimasukkan pada favorite
    fun addToFav(username: String, id: Int, ava: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val gitUser = UserFav(username, id, ava)
            userFavDao?.addUserToFav(gitUser)
            Log.d("Failure", gitUser.toString())
        }
    }

    // mengecek data dalam database
    suspend fun checkUser(id: Int) = userFavDao?.checkUserOnFav(id)

    // menghapus data dari database
    fun removeFromFav(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userFavDao?.removeUserFromFav(id)
            Log.d("Failure", id.toString())
        }
    }
}