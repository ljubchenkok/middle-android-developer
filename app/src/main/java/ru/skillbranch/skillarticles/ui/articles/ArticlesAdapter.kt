package ru.skillbranch.skillarticles.ui.articles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_article.view.*
import ru.skillbranch.skillarticles.R
import ru.skillbranch.skillarticles.data.ArticleItemData
import ru.skillbranch.skillarticles.extensions.dpToIntPx
import ru.skillbranch.skillarticles.extensions.format

class ArticlesAdapter (private val listener: (ArticleItemData) -> Unit) : ListAdapter<ArticleItemData, ArticleVH>(ArticleDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleVH {
        val containerView = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleVH(containerView)
    }

    override fun onBindViewHolder(holder: ArticleVH, position: Int) {
        holder.bind(getItem(position), listener)
    }

}

class ArticleDiffCallback: DiffUtil.ItemCallback<ArticleItemData>(){
    override fun areItemsTheSame(oldItem: ArticleItemData, newItem: ArticleItemData): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ArticleItemData, newItem: ArticleItemData): Boolean = oldItem == newItem
}

class ArticleVH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(
        item: ArticleItemData,
        listener: (ArticleItemData) -> Unit
    ){
        val posterSize: Int = containerView.context.dpToIntPx(64)
        val categorySize: Int = containerView.context.dpToIntPx(40)
        val cornerRadius: Int = containerView.context.dpToIntPx(8)

        Glide.with(containerView.context)
            .load(item.poster)
            .transform(CenterCrop(), RoundedCorners(cornerRadius))
            .override(posterSize)
            .into(containerView.iv_poster)

        Glide.with(containerView.context)
            .load(item.categoryIcon)
            .transform(CenterCrop(), RoundedCorners(cornerRadius))
            .override(categorySize)
            .into(containerView.iv_category)

        containerView.tv_date.text = item.date.format()
        containerView.tv_author.text = item.author
        containerView.tv_title.text = item.title
        containerView.tv_description.text = item.description
        containerView.tv_likes_count.text = item.likeCount.toString()
        containerView.tv_comments_count.text = item.commentCount.toString()
        containerView.tv_read_duration.text = "${item.readDuration} min read"

        itemView.setOnClickListener{listener(item)}

    }
}