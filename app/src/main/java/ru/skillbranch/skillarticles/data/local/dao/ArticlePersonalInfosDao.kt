package ru.skillbranch.skillarticles.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ru.skillbranch.skillarticles.data.local.entities.Article
import ru.skillbranch.skillarticles.data.local.entities.ArticlePersonalInfo

@Dao
interface ArticlePersonalInfosDao:BaseDao<ArticlePersonalInfo> {

    @Transaction
    suspend fun upsert(list: List<ArticlePersonalInfo>){
        insert(list).mapIndexed {index, recordResult ->  if(recordResult == -1L) list[index] else null }
            .filterNotNull().also{
            if(it.isNotEmpty()) update(it)
        }
    }


    @Query("""
        UPDATE article_personal_info SET is_like = NOT is_like, updated_at = CURRENT_TIMESTAMP 
        WHERE article_id = :articleId
    """)
    suspend fun toggleLike(articleId:String):Int

    @Query("""
        UPDATE article_personal_info SET is_bookmark = NOT is_bookmark, updated_at = CURRENT_TIMESTAMP
         WHERE article_id = :articleId
    """)
    fun toggleBookmark(articleId:String):Int

    @Query("""
        UPDATE article_personal_info SET is_bookmark = 1, updated_at = CURRENT_TIMESTAMP
         WHERE article_id = :articleId
    """)
    fun addBookmark(articleId:String):Int

    @Query("""
        UPDATE article_personal_info SET is_bookmark = 0, updated_at = CURRENT_TIMESTAMP
         WHERE article_id = :articleId
    """)
    fun removeBookmark(articleId:String):Int

    @Query("""
        SELECT is_bookmark FROM article_personal_info WHERE article_id = :articleId
    """)
    suspend fun isBookmarked(articleId:String):Boolean



    @Transaction
    suspend fun toggleBookmarkOrInsert(articleId:String) : Boolean{
        if(toggleBookmark(articleId) == 0) insert(ArticlePersonalInfo(articleId = articleId, isBookmark = true))
        return isBookmarked(articleId)
    }

    @Transaction
    suspend fun toggleLikeOrInsert(articleId:String) : Boolean {
        if(toggleLike(articleId) == 0) {
            insert(ArticlePersonalInfo(articleId = articleId, isLike = true))
            return true
        }
        return false

    }

    @Query("""SELECT * FROM article_personal_info""")
    fun findPersonalInfos(): LiveData<List<ArticlePersonalInfo>>

    @Query("""
        SELECT * 
        FROM article_personal_info
        WHERE article_id = :articleId
    """)
    fun findPersonalInfos(articleId: String): LiveData<ArticlePersonalInfo>


    @Query("SELECT * FROM article_personal_info WHERE article_id = :articleId")
    suspend fun findPersonalInfosTest(articleId: String): ArticlePersonalInfo



}