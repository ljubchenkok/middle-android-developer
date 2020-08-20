package ru.skillbranch.skillarticles.data.remote.res

data class ArticleCountsRes(
    val articleId: String,
    val likes: Int,
    val comments: Int,
    val readDuration: Int,
    val updatedAt: Long
)