package com.dapoidev.ourgithub.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dapoidev.ourgithub.data.UserFavDao
import com.dapoidev.ourgithub.data.database.UserFavDatabase

class GithubContentProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "com.dapoidev.ourgithub"
        private const val ID_DATA = 1
        private const val TABLE_NAME = "favorite"

        private val sUriMathcer = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMathcer.addURI(AUTHORITY, TABLE_NAME, ID_DATA)
        }
    }

    private lateinit var userFavDao: UserFavDao

    // menyiapkan data yang akan ditampilkan pada consumer app
    override fun onCreate(): Boolean {
        val let = context?.let {
            UserFavDatabase.getDB(it)?.userFavDao()
        }
        userFavDao = if (let != null) let else throw KotlinNullPointerException()
        return false
    }

    // mmenyeleksi data yang dijadikan cursor pada consumer app
    override fun query(uri: Uri, projection: Array<out String>?, selection: String?,
        selectionArgs: Array<out String>?, sortOrder: String?, ): Cursor? {
        val cursor: Cursor?
        when (sUriMathcer.match(uri)) {
            ID_DATA -> {
                cursor = userFavDao.findFavoriteList()
                if (context != null) {
                    cursor.setNotificationUri(context?.contentResolver, uri)
                }
            } else -> {
                cursor = null
            }
        }
        return cursor
    }

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<out String>?,): Int = 0
}