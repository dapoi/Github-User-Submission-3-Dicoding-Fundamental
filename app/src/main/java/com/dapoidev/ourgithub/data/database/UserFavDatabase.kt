package com.dapoidev.ourgithub.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dapoidev.ourgithub.data.UserFav
import com.dapoidev.ourgithub.data.UserFavDao

// membuat database, naikan versi database ketika ada perubahan pada abstrak kelas database
@Database (
    entities = [UserFav::class],
    version = 1
)

abstract class UserFavDatabase : RoomDatabase() {
    abstract fun userFavDao() : UserFavDao

    companion object {
        @Volatile
        private var INSTANCE : UserFavDatabase? = null

        fun getDB(mContext: Context) : UserFavDatabase? {
            // double check null digunakan agar ketika thread unlocked, tidak membuat instance baru
            if (INSTANCE == null) {
                synchronized(UserFavDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(mContext.applicationContext,
                            UserFavDatabase::class.java, "database_user").build()
                    }
                }
            }
            return INSTANCE
        }
    }

}