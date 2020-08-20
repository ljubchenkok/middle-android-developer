package ru.skillbranch.skillarticles.data.remote.res

import ru.skillbranch.skillarticles.data.local.entities.Author

data class AuthorRes(
    val id: String,
    val name: String,
    val avatar: String
)

fun AuthorRes.toAuthor() = Author(
    userId = id,
    name = name,
    avatar = avatar
)