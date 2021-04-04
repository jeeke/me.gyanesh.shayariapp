package me.gyanesh.shayariapp.ui.categoryDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nblik.app.util.copyToClipboard
import me.gyanesh.shayariapp.data.PreferenceProvider
import me.gyanesh.shayariapp.data.model.Shayari
import me.gyanesh.shayariapp.databinding.HolderShayariBinding
import me.gyanesh.shayariapp.util.*
import java.util.*


class ShayariAdapter :
    ListAdapter<Shayari, ShayariAdapter.CommunityViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Shayari> =
            object : DiffUtil.ItemCallback<Shayari>() {
                override fun areItemsTheSame(
                    oldItem: Shayari,
                    newItem: Shayari
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Shayari,
                    newItem: Shayari
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CommunityViewHolder(
            HolderShayariBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: CommunityViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.binding.shayari = item
        val c = getRandomColor()
        holder.binding.card.setCardBackgroundColor(c)
        holder.binding.ssLayout.setBackgroundColor(c)
        holder.binding.btnCopy.setOnClickListener {
            it.context.copyToClipboard(item.content)
            it.context.toastSuccess("Shayari Copied Successfully!")
        }
        holder.binding.btnShare.setOnClickListener {
            val filepath = "shayri.png"
            val bitmap = holder.binding.ssLayout.screenShot()
            it.context.storeBitmap(filepath, bitmap)
            it.context.shareImage(filepath)
        }
        holder.binding.btnSave.setOnClickListener {
            PreferenceProvider.addToSavedShayaris(item.roomId.toString())
            it.context.toastSuccess("Shayari Saved Successfully!")
        }
    }

    inner class CommunityViewHolder(
        val binding: HolderShayariBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
