package ru.skillbranch.skillarticles.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_chose_category_dialog.*
import ru.skillbranch.skillarticles.R
import ru.skillbranch.skillarticles.viewmodels.articles.ArticlesViewModel


class ChoseCategoryDialog : DialogFragment()  {
    private val viewModel : ArticlesViewModel by activityViewModels()
    private val args : ChoseCategoryDialogArgs by navArgs()
    private val categories by lazy { args.categories }
    private val selectedCategories = mutableSetOf<String>()
    private var customView: View? = null

    private val layout = R.layout.fragment_chose_category_dialog
    private val categoriesAdapter = CategoriesAdapter {category, isChecked ->
        if(isChecked)selectedCategories.add(category.categoryId)
        else selectedCategories.remove(category.categoryId)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        selectedCategories.addAll(args.selectedCategories)
        val adb =AlertDialog.Builder(requireContext())
            .setTitle("Chose category")
            .setPositiveButton("Apply"){_, _ ->
                viewModel.applyCategories(selectedCategories.toList())
            }
            .setNegativeButton("Reset"){_,_ ->
                viewModel.applyCategories(emptyList())
            }
        customView = activity?.layoutInflater?.inflate(layout, null)
        adb.setView(customView)
        return adb.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return customView ?: super.onCreateView(inflater, container, savedInstanceState)

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(categories_list) {
            layoutManager = LinearLayoutManager(context)
            adapter = categoriesAdapter
        }

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val saved = savedInstanceState?.getStringArray(::ChoseCategoryDialog.name)
        if(saved != null) {
            selectedCategories.clear()
            selectedCategories.addAll(saved)
        }
        categoriesAdapter.submitList(categories.map {it to selectedCategories.contains(it.categoryId)})

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putStringArray(::ChoseCategoryDialog.name,selectedCategories.toTypedArray())
        super.onSaveInstanceState(outState)
    }



}