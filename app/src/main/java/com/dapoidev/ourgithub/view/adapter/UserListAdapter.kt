package com.dapoidev.ourgithub.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dapoidev.ourgithub.databinding.ItemListUserBinding
import com.dapoidev.ourgithub.model.UserModel

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    // memproses interface yang telah kita buat
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    // membuat fungsi set untuk melakukan daur ulang sekaligus memberi tau kepada user model
    private val list = ArrayList<UserModel>()
    fun setterList(users: ArrayList<UserModel>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    // mencocokkan data dengan komponen
    inner class UserViewHolder(private val binding: ItemListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserModel) {
            Glide.with(itemView)
                .load(user.avatar_url)
                .into(binding.imgGitAva)

            binding.tvGitUsername.text = user.login

            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
        }
    }

    // berfungsi untuk menghubungkan dengan layout item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemListUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return UserViewHolder(view)
    }

    // berfungsi untuk memberi data ke viewholder sesuai posisinya
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    // menentukan jumlah item yang ditampilkan
    override fun getItemCount(): Int = list.size

    // persiapan agar setiap user bisa diklik
    interface OnItemClickCallback {
        fun onItemClicked(data: UserModel)
    }
}
