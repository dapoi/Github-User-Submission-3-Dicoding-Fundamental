package com.dapoidev.ourgithub.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dapoidev.ourgithub.data.UserFav
import com.dapoidev.ourgithub.databinding.ActivityFavoriteBinding
import com.dapoidev.ourgithub.model.UserModel
import com.dapoidev.ourgithub.view.adapter.UserListAdapter
import com.dapoidev.ourgithub.viewModel.FavoriteViewModel
import java.util.ArrayList

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favBinding: ActivityFavoriteBinding
    private lateinit var favViewModel: FavoriteViewModel
    private lateinit var favAdapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favBinding.root)

        favAdapter = UserListAdapter()
        favAdapter.notifyDataSetChanged()

        favAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserModel) {
                val intent = Intent(this@FavoriteActivity, UserDetailActivity::class.java)
                intent.apply {
                    putExtra(UserDetailActivity.USERNAME_PACKAGE, data.login)
                    putExtra(UserDetailActivity.ID_PACKAGE, data.id)
                    putExtra(UserDetailActivity.AVA_PACKAGE, data.avatar_url)
                    startActivity(intent)
                }
            }
        })

        favViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favViewModel.getListFavUser()?.observe(this, {
            if (it != null) {
                val listMap = listMap(it)
                favAdapter.setterList(listMap)
            }
        })

        favBinding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = favAdapter
        }
    }

    private fun listMap(users: List<UserFav>): ArrayList<UserModel> {
        val userList = ArrayList<UserModel>()
        for (user in users) {
            val mapUser = UserModel(user.login, user.id, user.avatar_url)
            userList.add(mapUser)
        }
        return userList
    }
}