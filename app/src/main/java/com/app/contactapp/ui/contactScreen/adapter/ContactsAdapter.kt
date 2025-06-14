package com.app.contactapp.ui.contactScreen.adapter

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.contactapp.R
import com.app.contactapp.databinding.ItemContactBinding
import com.app.contactapp.domain.model.response.UserData

class ContactsAdapter(val onItemClick: (pos: Int, model: UserData, src: String) -> Unit) :
    ListAdapter<UserData, ContactsAdapter.MyViewHolder>(
        ContactsAdapterCallback
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding= ItemContactBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(getItem(position))
    }

    inner class MyViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(data:UserData){
            val colors = listOf(
                R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5,
                R.color.color6, R.color.color7, R.color.color8, R.color.color9, R.color.color10
            )

            binding.apply {
                tvName.text = data.fullName
                tvFirstLetter.text = data.fullName[0].toString()
                val colorIndex = adapterPosition % colors.size
                val color = ContextCompat.getColor(itemView.context, colors[colorIndex])

                val drawable = imgCircle.background.mutate() as GradientDrawable
                drawable.setColor(color)
            }
        }
    }

    object ContactsAdapterCallback : DiffUtil.ItemCallback<UserData>() {

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