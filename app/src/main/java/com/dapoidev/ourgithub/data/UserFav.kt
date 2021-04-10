package com.dapoidev.ourgithub.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// membuat table
@Entity(tableName = "favorite")
data class UserFav(
    val login: String,

    // primary key merupakan sebuah identitas unik
    @PrimaryKey
    val id: Int,
    val avatar_url: String
): Serializable
