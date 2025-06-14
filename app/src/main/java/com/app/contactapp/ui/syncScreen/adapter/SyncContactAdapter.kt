package com.app.contactapp.ui.syncScreen.adapter
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.contactapp.R
import com.app.contactapp.databinding.ItemSyncedContactBinding
import com.app.contactapp.domain.model.response.UserData

class SyncContactAdapter(val onItemClick: (pos: Int, model: UserData, src: String) -> Unit) :
    ListAdapter<UserData, SyncContactAdapter.MyViewHolder>(
        SyncContactAdapterCallback
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding= ItemSyncedContactBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(getItem(position))
    }

    inner class MyViewHolder(private val binding: ItemSyncedContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val colors = listOf(
            R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5,
            R.color.color6, R.color.color7, R.color.color8, R.color.color9, R.color.color10
        )
        fun setData(data:UserData){
            binding.apply {
                tvName.text = data.fullName
                tvEmail.text = data.email
                tvPhone.text = data.phone
                tvJob.text = data.course
                val colorIndex = adapterPosition % colors.size
                val color = ContextCompat.getColor(itemView.context, colors[colorIndex])
                root.setCardBackgroundColor(color)
                ivEdit.setOnClickListener {
                    onItemClick(adapterPosition,data,"EDIT")
                }
            }
        }
    }

    object SyncContactAdapterCallback : DiffUtil.ItemCallback<UserData>() {

        override fun areItemsTheSame(
            oldItem: UserData,
            newItem: UserData
        ): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(
            oldItem: UserData,
            newItem: UserData
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun submitList(list: MutableList<UserData>?) {
        super.submitList(list?.let { ArrayList(it) })
    }
}