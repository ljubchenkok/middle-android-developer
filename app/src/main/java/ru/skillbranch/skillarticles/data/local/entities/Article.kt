package ru.skillbranch.skillarticles.data.local.entities

import androidx.room.*
import ru.skillbranch.skillarticles.data.local.MarkdownConverter
import ru.skillbranch.skillarticles.data.repositories.MarkdownElement
import java.util.*

@Entity(tableName = "Articles")
data class Article(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    @Embedded(prefix = "author_")
    val author: Author,
    @ColumnInfo(name = "category_id")
    val categoryId: String,
    val poster: String,
    val date: Date,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Date
)

data class Author(
    @ColumnInfo(name = "user_id")
    val userId: String,
    val avatar: String? = null,
    val name: String
)

@DatabaseView(
    """
    SELECT id, date, author_name AS author, author_avatar, article.title as title, description, poster, article.category_id as category_id, 
    counts.likes as like_count, counts.comments as comment_count, counts.read_duration as read_duration,
    category.title as category, category.icon as category_icon, personal.is_bookmark as is_bookmark
    FROM articles as article
    INNER JOIN article_counts as counts ON counts.article_id = id 
    INNER JOIN article_categories as category ON category.category_id = article.category_id
    LEFT JOIN article_personal_info as personal ON personal.article_id = id
"""
)
data class ArticleItem(
    val id: String,
    val date: Date = Date(),
    val author: String,
    @ColumnInfo(name = "author_avatar")
    val authorAvatar: String?,
    val title: String,
    val description: String,
    val poster: String,
    val category: String,
    @ColumnInfo(name = "category_id")
    val categoryId: String = "0",
    @ColumnInfo(name = "category_icon")
    val categoryIcon: String ,
    @ColumnInfo(name = "like_count")
    val likeCount: Int = 0,
    @ColumnInfo(name = "comment_count")
    val commentCount: Int = 0,
    @ColumnInfo(name = "read_duration")
    val readDuration: Int = 0,
    @ColumnInfo(name = "is_bookmark")
    val isBookmark: Boolean = false
)


@DatabaseView(
    """
       SELECT id, article.title AS title, description, author_user_id, author_avatar, author_name, date, 
        category.category_id AS category_category_id, category.title AS category_title, category.icon AS category_icon,
        content.share_link AS share_link, content.content AS content,
        personal.is_bookmark AS is_bookmark, personal.is_like AS is_like, content.source as source
        FROM articles AS article
        INNER JOIN article_categories AS category ON category.category_id = article.category_id
        LEFT JOIN article_content AS content ON content.article_id = id
        LEFT JOIN article_personal_info AS personal ON personal.article_id = id
    """
)
@TypeConverters(MarkdownConverter::class)
data class ArticleFull(
    val id: String,
    val title: String,
    val description: String,
    @Embedded(prefix = "author_")
    val author: Author,
    @Embedded(prefix = "category_")
    val category: Category,
    @ColumnInfo(name = "share_link")
    val shareLink:String? = null,
    @ColumnInfo(name = "is_bookmark")
    val isBookmark: Boolean = false,
    @ColumnInfo(name = "is_like")
    val isLike:Boolean = false,
    val date: Date,
    val content: List<MarkdownElement>? = null,
    val source: String? = null,
    @Relation(
        parentColumn = "id",
        entityColumn = "tag",
        associateBy = Junction(
            value = ArticleTagXRef::class,
            parentColumn = "a_id",
            entityColumn = "t_id"
        )
    )
    val tags: List<Tag> = emptyList()

)

data class ArticleWithContent(
    val id:String,
    val title:String,
    val description:String,
    @Relation(
        parentColumn = "id",
        entityColumn = "article_id"
    )
    val content:ArticleContent
)

data class CategoryWithArticles(
    @Embedded
    val category: Category,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "category_id"
    )
    val articles: List<Article>
)

data class ArticleWithShareLink(
    val id:String,
    val title:String,
    val description:String,
    @Relation(
        entity = ArticleContent::class,
        parentColumn = "id",
        entityColumn = "article_id",
        projection = ["share_link"]
    )
    val link:String
)