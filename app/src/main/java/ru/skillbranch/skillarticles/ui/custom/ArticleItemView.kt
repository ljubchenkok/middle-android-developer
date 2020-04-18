package ru.skillbranch.skillarticles.ui.custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.skillbranch.skillarticles.R
import ru.skillbranch.skillarticles.data.ArticleItemData
import ru.skillbranch.skillarticles.extensions.attrValue
import ru.skillbranch.skillarticles.extensions.dpToIntPx
import ru.skillbranch.skillarticles.extensions.format
import kotlin.math.max

class ArticleItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private var likesCount: TextView
    private var isBookmark: ImageView
    private var readDuration: TextView
    private var commentsCount: TextView
    private var comments: ImageView
    private var likes: ImageView
    private var description: TextView
    private var category: ImageView
    private var poster: ImageView
    private val date: TextView
    private val author: TextView
    private var title: TextView

    private val posterSize = context.dpToIntPx(64)
    private val categorySize = context.dpToIntPx(40)
    private val cornerRadius = context.dpToIntPx(8)
    private val iconSize = context.dpToIntPx(16)

    private val spacingLarge = context.dpToIntPx(16)
    private val spacingNormal = context.dpToIntPx(8)
    private val spacingSmall = context.dpToIntPx(4)

    init {
        setPadding(spacingLarge,spacingLarge,spacingLarge,spacingLarge)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        date = TextView(context).apply {
            setTextColor(context.getColor(R.color.color_gray))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            id = R.id.tv_title

        }
        addView(date)
        author = TextView(context).apply {
            setTextColor(context.attrValue(R.attr.colorPrimary))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            id = R.id.tv_author
        }
        addView(author)
        poster = ImageView(context).apply {
            id = R.id.iv_poster
        }
        addView(poster)

        category = ImageView(context).apply {
            id = R.id.iv_category
        }
        addView(category)

        title = TextView(context).apply {
            setTextColor(context.attrValue(R.attr.colorPrimary))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
            setTypeface(typeface, Typeface.BOLD)
            id = R.id.tv_title
        }
        addView(title)

        description = TextView(context).apply {
            setTextColor(context.getColor(R.color.color_gray))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            id = R.id.tv_description
        }
        addView(description)

        likes = ImageView(context).apply {
            setImageResource(R.drawable.ic_favorite_black_24dp)
            setColorFilter(
                context.getColor(R.color.color_gray)
            )
            id = R.id.iv_likes
        }
        addView(likes)

        likesCount = TextView(context).apply {
            setTextColor(context.getColor(R.color.color_gray))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            id = R.id.tv_likes_count
        }
        addView(likesCount)

        comments = ImageView(context).apply {
            setImageResource(R.drawable.ic_insert_comment_black_24dp)
            setColorFilter(
                context.getColor(R.color.color_gray)
            )
            id = R.id.iv_comments
        }
        addView(comments)

        commentsCount = TextView(context).apply {
            setTextColor(context.getColor(R.color.color_gray))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            id = R.id.tv_comments_count
        }
        addView(commentsCount)

        readDuration = TextView(context).apply {
            setTextColor(context.getColor(R.color.color_gray))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            id = R.id.tv_read_duration
        }
        addView(readDuration)

        isBookmark = ImageView(context).apply {
            setImageResource(R.drawable.bookmark_states)
            setColorFilter(
                context.getColor(R.color.color_gray)
            )
            id = R.id.iv_bookmark
        }
        addView(isBookmark)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var usedHeight = paddingTop
        val width = View.getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        measureChild(date, widthMeasureSpec, heightMeasureSpec)
        measureChild(author, widthMeasureSpec, heightMeasureSpec)
        usedHeight += max(date.measuredHeight, author.measuredHeight)
        usedHeight += spacingLarge

        val titleWidth =
            width - paddingLeft - paddingRight - posterSize - categorySize / 2 - spacingSmall
        val titleSpec = MeasureSpec.makeMeasureSpec(titleWidth, MeasureSpec.AT_MOST)
        measureChild(title, titleSpec, heightMeasureSpec)
        usedHeight += max(title.measuredHeight, posterSize + categorySize / 2)
        usedHeight += spacingNormal

        measureChild(description, widthMeasureSpec, heightMeasureSpec)
        usedHeight += description.measuredHeight

        measureChild(likesCount, widthMeasureSpec, heightMeasureSpec)
        measureChild(commentsCount, widthMeasureSpec, heightMeasureSpec)
        measureChild(readDuration, widthMeasureSpec, heightMeasureSpec)

        usedHeight += listOf(
            likesCount.measuredHeight,
            commentsCount.measuredHeight,
            readDuration.measuredHeight,
            iconSize
        ).max() ?: 0
        usedHeight += paddingBottom
        setMeasuredDimension(width, usedHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val bodyWidth = r - l - paddingLeft - paddingRight
        val left = paddingLeft
        val right = paddingLeft + bodyWidth
        val top = paddingTop

        val dateRight = date.measuredWidth + paddingLeft
        date.layout(left, top, dateRight, date.measuredHeight + paddingTop)

        val authorLeft = dateRight + spacingLarge
        val authorBottom = author.measuredHeight + paddingTop
        author.layout(authorLeft, paddingTop, right, authorBottom)

        val barrierTop = max(date.measuredHeight, author.measuredHeight) + paddingTop
        val barrierBottom =
            barrierTop + spacingNormal*2 + max(title.measuredHeight, posterSize + categorySize / 2)

        val posterLeft = right - posterSize
        val posterTop = barrierTop + (barrierBottom - barrierTop) / 2 - (posterSize + categorySize/2) / 2
        val posterBottom = posterTop + posterSize
        poster.layout(posterLeft, posterTop, right, posterBottom)

        val categoryLeft = posterLeft - categorySize / 2
        val categoryTop = posterBottom - categorySize/2
        val categoryBottom = categoryTop + categorySize
        val categoryRight = categoryLeft + categorySize
        category.layout(categoryLeft, categoryTop, categoryRight, categoryBottom)

        val titleTop = barrierTop + (barrierBottom - barrierTop) / 2 - title.measuredHeight / 2 - spacingSmall
        val titleRight = categoryLeft - spacingSmall
        val titleBottom = titleTop + title.measuredHeight
        title.layout(left, titleTop, titleRight, titleBottom)
        title.maxWidth = width-paddingLeft-paddingRight-posterSize-categorySize/2 + spacingNormal

        var descriptionBottom = barrierBottom + description.measuredHeight
        description.layout(left, barrierBottom, right, descriptionBottom)
        descriptionBottom += spacingNormal

        val likesRight = left + iconSize
        val likesBottom = descriptionBottom + iconSize
        likes.layout(left, descriptionBottom, likesRight, likesBottom)

        val likesCountLeft = left + iconSize + spacingNormal
        val likesCountRight = likesCountLeft + likesCount.measuredWidth
        val likesCountBottom = descriptionBottom + likesCount.measuredHeight
        likesCount.layout(likesCountLeft, descriptionBottom, likesCountRight, likesCountBottom)

        val commentsLeft = likesCountLeft + likesCount.measuredWidth + spacingLarge
        val commentRight = commentsLeft + iconSize
        val commentsBottom = descriptionBottom + iconSize
        comments.layout(commentsLeft, descriptionBottom, commentRight, commentsBottom)

        val commentsCountLeft = commentsLeft + iconSize + spacingNormal
        val commentsCountRight = commentsCountLeft + commentsCount.measuredWidth
        val commentsCountBottom = descriptionBottom + commentsCount.measuredHeight
        commentsCount.layout(
            commentsCountLeft,
            descriptionBottom,
            commentsCountRight,
            commentsCountBottom
        )

        val bookmarkLeft = right - iconSize
        val bookmarkBottom = descriptionBottom + iconSize
        isBookmark.layout(bookmarkLeft, descriptionBottom, right, bookmarkBottom)

        val readDurationLeft = commentsCountLeft + commentsCount.measuredWidth + spacingLarge
        val readDurationRight = right - iconSize - spacingLarge
        val readDurationBottom = descriptionBottom + readDuration.measuredHeight
        readDuration.layout(
            readDurationLeft,
            descriptionBottom,
            readDurationRight,
            readDurationBottom
        )

    }

    fun bind(data: ArticleItemData) {
        date.text = data.date.format()
        author.text = data.author
        title.text = data.title
        description.text = data.description
        likesCount.text = "${data.likeCount}"
        commentsCount.text = "${data.commentCount}"
        readDuration.text = context.getString(R.string.duration, data.readDuration)

        Glide.with(context)
            .load(data.poster)
            .transform(CenterCrop(), RoundedCorners(cornerRadius))
            .override(posterSize)
            .into(poster)

        Glide.with(context)
            .load(data.categoryIcon)
            .transform(CenterCrop(), RoundedCorners(cornerRadius))
            .override(categorySize)
            .into(category)
    }


}