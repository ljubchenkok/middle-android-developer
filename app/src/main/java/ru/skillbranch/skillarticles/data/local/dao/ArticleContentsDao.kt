package ru.skillbranch.skillarticles.data.local.dao

import androidx.room.*
import ru.skillbranch.skillarticles.data.local.entities.ArticleContent
import ru.skillbranch.skillarticles.data.local.entities.ArticleItem

@Dao
interface ArticleContentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj:ArticleContent) : Long

    @Query("""
        DELETE
        FROM article_content 
        WHERE article_id = :articleId
    """)
    suspend fun delete(articleId: String)

    @Query("SELECT * FROM article_content")
    suspend fun findArticlesContentsTest(): List<ArticleContent>
}