package me.gyanesh.shayariapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.holder_category.view.*
import me.gyanesh.shayariapp.R
import me.gyanesh.shayariapp.data.model.Category
import me.gyanesh.shayariapp.databinding.HolderCategoryBinding
import me.gyanesh.shayariapp.util.getRandomGradientDrawable


class CategoryAdapter :
    ListAdapter<Category, CategoryAdapter.CommunityViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Category> =
            object : DiffUtil.ItemCallback<Category>() {
                override fun areItemsTheSame(
                    oldItem: Category,
                    newItem: Category
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Category,
                    newItem: Category
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CommunityViewHolder(
            HolderCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: CommunityViewHolder,
        position: Int
    ) {
        holder.binding.category = getItem(position)
        holder.binding.root.background_container.setBackgroundDrawable(getRandomGradientDrawable())
        holder.binding.root.setOnClickListener {
            it.findNavController().navigate(
                R.id.categoryDetailFragment,
                bundleOf(
                    "category" to getItem(position).category_name
                )
            )
        }
    }

    inner class CommunityViewHolder(
        val binding: HolderCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
