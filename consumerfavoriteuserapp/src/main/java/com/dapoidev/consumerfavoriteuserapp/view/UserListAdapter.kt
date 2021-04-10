package com.dapoidev.consumerfavoriteuserapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dapoidev.consumerfavoriteuserapp.databinding.ItemListUserBinding
import com.dapoidev.consumerfavoriteuserapp.model.UserModel

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {
    private val list = ArrayList<UserModel>()

    fun setterList(users: ArrayList<UserModel>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserModel) {
            Glide.with(itemView)
                .load(user.avatar_url)
                .into(binding.imgGitAva)

            binding.tvGitUsername.text = user.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemListUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
