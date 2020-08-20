package ru.skillbranch.skillarticles.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ru.skillbranch.skillarticles.data.local.entities.ArticleCounts

@Dao
interface ArticleCountsDao:BaseDao<ArticleCounts> {
    @Transaction
    suspend fun upsert(list: List<ArticleCounts>){
        insert(list)
            .mapIndexed {index, recordResult -> if(recordResult == -1L) list[index] else null }
            .filterNotNull().also{
            if(it.isNotEmpty()) update(it)
        }
    }

    @Query("""SELECT * FROM article_counts""")
    fun findArticleCounts(): LiveData<List<ArticleCounts>>

    @Query("""
        SELECT * 
        FROM article_counts 
        WHERE article_id = :articleId
    """)
    fun findArticleCounts(articleId: String): LiveData<ArticleCounts>

//    fun incrementLikeOrInsert(articleId: String)

    @Query("""
        UPDATE article_counts SET likes = likes + 1, updated_at = CURRENT_TIMESTAMP
        WHERE article_id = :articleId
    """)
    suspend fun incrementLike(articleId: String)

    @Query("""
        UPDATE article_counts SET likes = MAX(0, likes - 1), updated_at = CURRENT_TIMESTAMP
        WHERE article_id = :articleId
    """)
    suspend fun decrementLike(articleId: String)

    @Query("""
        UPDATE article_counts SET likes = :counts, updated_at = CURRENT_TIMESTAMP
        WHERE article_id = :articleId
    """)
    suspend fun updateLike(articleId: String, counts: Int)

    @Query("""
        UPDATE article_counts SET comments = comments + 1, updated_at = CURRENT_TIMESTAMP
        WHERE article_id = :articleId
    """)
    suspend fun incrementCommentsCount(articleId: String)

    @Query("""
        UPDATE article_counts SET comments = MAX(0, comments - 1), updated_at = CURRENT_TIMESTAMP
        WHERE article_id = :articleId
    """)
    suspend fun decrementCommentsCount(articleId: String)

    @Query("""
        SELECT comments from article_counts WHERE article_id = :articleId
    """)
    fun getCommentsCount(articleId: String):LiveData<Int>

    @Query("""
        UPDATE article_counts SET comments = :counts, updated_at = CURRENT_TIMESTAMP
        WHERE article_id = :articleId
    """)
    suspend fun updateCommentsCount(articleId: String, counts: Int)

}
