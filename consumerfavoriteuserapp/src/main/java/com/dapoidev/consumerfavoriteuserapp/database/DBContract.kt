package com.dapoidev.consumerfavoriteuserapp.database

import android.net.Uri
import android.provider.BaseColumns

object DBContract {
    const val AUTHORITY = "com.dapoidev.ourgithub"
    const val SCHEME = "content"

    internal class Column: BaseColumns {
        companion object {
            private const val TABLE_NAME = "favorite"
            const val USERNAME = "login"
            const val ID = "id"
            const val AVATAR_URL = "avatar_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}