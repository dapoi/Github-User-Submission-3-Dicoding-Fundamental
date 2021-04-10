package com.dapoidev.consumerfavoriteuserapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dapoidev.consumerfavoriteuserapp.database.DBContract
import com.dapoidev.consumerfavoriteuserapp.database.MappingHelper
import com.dapoidev.consumerfavoriteuserapp.model.UserModel

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {
    private var favList = MutableLiveData<ArrayList<UserModel>>()

    fun setListFavUser(context: Context) {
        val dataCursor = context.contentResolver.query(
            DBContract.Column.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val cursorToArrayList = MappingHelper.mapCursorToArrayList(dataCursor)
        favList.postValue(cursorToArrayList)
    }

    fun getListFavUser(): LiveData<ArrayList<UserModel>> = favList
}