package com.furqoncreative.githubusers3.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.furqoncreative.githubusers3.data.entities.userdata.UserData
import com.furqoncreative.githubusers3.databinding.ItemRowGithubUserBinding

class SearchUserAdapter(private val listener: GithubUserItemListener) :
    RecyclerView.Adapter<GithubUserViewHolder>() {

    interface GithubUserItemListener {
        fun onClickedUser(userData: UserData)
    }

    private val items = ArrayList<UserData>()

    fun setItems(items: ArrayList<UserData>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
        val binding: ItemRowGithubUserBinding =
            ItemRowGithubUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GithubUserViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) =
        holder.bind(items[position])
}

class GithubUserViewHolder(
    private val itemBinding: ItemRowGithubUserBinding,
    private val listener: SearchUserAdapter.GithubUserItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var user: UserData

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: UserData) {
        this.user = item
        itemBinding.txtUsername.text = item.login
        Glide.with(itemBinding.root)
            .load(item.avatarUrl)
            .transform(CircleCrop())
            .into(itemBinding.imgAvatar)
    }

    override fun onClick(v: View?) {
        listener.onClickedUser(user)
    }
}

