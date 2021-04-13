package com.furqoncreative.favoriteuser.ui.detailuser.following

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.furqoncreative.favoriteuser.data.entities.followings.FollowingsItem
import com.furqoncreative.favoriteuser.databinding.ItemRowGithubUserBinding

class FollowingAdapter : RecyclerView.Adapter<FollowingViewHolder>() {

    private val items = ArrayList<FollowingsItem>()

    fun setItems(items: ArrayList<FollowingsItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding: ItemRowGithubUserBinding =
            ItemRowGithubUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) =
        holder.bind(items[position])
}

class FollowingViewHolder(
    private val itemBinding: ItemRowGithubUserBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    private lateinit var user: FollowingsItem

    fun bind(item: FollowingsItem) {
        this.user = item
        itemBinding.txtUsername.text = item.login
        Glide.with(itemBinding.root)
            .load(item.avatarUrl)
            .transform(CircleCrop())
            .into(itemBinding.imgAvatar)
    }
}

