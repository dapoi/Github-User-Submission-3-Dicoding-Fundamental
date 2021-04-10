 package com.dapoidev.ourgithub.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// DAO (Data Access Object), yang bertanggungjawab atas segala kejadian CRUD
@Dao
interface UserFavDao {
    // menginput data
    @Insert
    suspend fun addUserToFav(userFav: UserFav)

    // mengecek data apakah sudah ada di database atau belum
    @Query("SELECT count(*) FROM favorite WHERE favorite.id = :id")
    suspend fun checkUserOnFav(id: Int) : Int

    // menghapus data dari database
    @Query("DELETE FROM favorite WHERE favorite.id = :id")
    suspend fun removeUserFromFav(id: Int) : Int

    // menampilkan favorite list
    @Query("SELECT * FROM favorite")
    fun getUserOnFav(): LiveData<List<UserFav>>

    // consumer app favorite list
    @Query("SELECT * FROM favorite")
    fun findFavoriteList(): Cursor
}