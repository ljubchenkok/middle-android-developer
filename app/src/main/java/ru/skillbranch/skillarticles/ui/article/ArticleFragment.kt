package ru.skillbranch.skillarticles.ui.article

import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import ru.skillbranch.skillarticles.R
import ru.skillbranch.skillarticles.ui.base.BaseFragment
import ru.skillbranch.skillarticles.viewmodels.article.ArticleViewModel
import ru.skillbranch.skillarticles.viewmodels.base.ViewModelFactory

class ArticleFragment : BaseFragment<ArticleViewModel>(), IArticleView {
    override val viewModel:ArticleViewModel by viewModels {
        ViewModelFactory(
            owner = this,
            params = "0"
        )
    }
    override val layout: Int = R.layout.fragment_article

    override fun setupViews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSearchBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideSearchBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
