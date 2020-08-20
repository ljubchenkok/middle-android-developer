package ru.skillbranch.skillarticles.data.remote.res

import ru.skillbranch.skillarticles.data.local.entities.Category

data class CategoryRes(
    val id: String,
    val title: String,
    val icon: String
)

fun CategoryRes.toCategory() = Category(
    categoryId = id,
    title =  title,
    icon = icon
)