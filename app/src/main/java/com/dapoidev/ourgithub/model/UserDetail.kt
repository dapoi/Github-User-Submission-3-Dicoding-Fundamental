package com.dapoidev.ourgithub.model

data class UserDetail(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val name: String,
    val followers: Int,
    val following: Int,
    val public_repos: Int,
    val company: String,
    val location: String
)