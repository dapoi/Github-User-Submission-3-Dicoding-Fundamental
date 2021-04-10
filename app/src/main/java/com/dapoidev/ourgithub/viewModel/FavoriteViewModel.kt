package com.dapoidev.ourgithub.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dapoidev.ourgithub.data.UserFav
import com.dapoidev.ourgithub.data.UserFavDao
import com.dapoidev.ourgithub.data.database.UserFavDatabase

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {
    private var userFavDao: UserFavDao?
    private var userFavDB: UserFavDatabase? = UserFavDatabase.getDB(app)

    init {
        userFavDao = userFavDB?.userFavDao()
    }

    fun getListFavUser(): LiveData<List<UserFav>>? {
       return userFavDao?.getUserOnFav()
    }
}