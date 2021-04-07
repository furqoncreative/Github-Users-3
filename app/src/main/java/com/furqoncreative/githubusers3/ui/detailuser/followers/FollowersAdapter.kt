package com.furqoncreative.githubusers3.ui.detailuser.followers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.furqoncreative.githubusers3.data.entities.followers.FollowersItem
import com.furqoncreative.githubusers3.databinding.ItemRowGithubUserBinding

class FollowersAdapter : RecyclerView.Adapter<FollowersViewHolder>() {

    private val items = ArrayList<FollowersItem>()

    fun setItems(items: ArrayList<FollowersItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val binding: ItemRowGithubUserBinding =
            ItemRowGithubUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowersViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) =
        holder.bind(items[position])
}

class FollowersViewHolder(
    private val itemBinding: ItemRowGithubUserBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    private lateinit var user: FollowersItem

    fun bind(item: FollowersItem) {
        this.user = item
        itemBinding.txtUsername.text = item.login
        Glide.with(itemBinding.root)
            .load(item.avatarUrl)
            .transform(CircleCrop())
            .into(itemBinding.imgAvatar)
    }
}

