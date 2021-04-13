package com.furqoncreative.favoriteuser.ui.favoriteuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.furqoncreative.favoriteuser.data.entities.userdata.UserData
import com.furqoncreative.favoriteuser.databinding.ItemRowGithubUserBinding


class FavoriteUserAdapter(private val listener: GithubUserItemListener) :
    RecyclerView.Adapter<GithubUserViewHolder>() {
    interface GithubUserItemListener {
        fun onClickedUser(userData: UserData)
    }

    val items = ArrayList<UserData>()

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
    private val listener: FavoriteUserAdapter.GithubUserItemListener,
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var userData: UserData

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: UserData) {
        this.userData = item
        itemBinding.txtUsername.text = item.login
        Glide.with(itemBinding.root)
            .load(item.avatarUrl)
            .transform(CircleCrop())
            .into(itemBinding.imgAvatar)
    }

    override fun onClick(v: View?) {
        listener.onClickedUser(userData)
    }
}

