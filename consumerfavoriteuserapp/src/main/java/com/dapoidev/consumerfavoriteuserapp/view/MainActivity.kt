package com.dapoidev.consumerfavoriteuserapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dapoidev.consumerfavoriteuserapp.databinding.ActivityMainBinding
import com.dapoidev.consumerfavoriteuserapp.viewmodel.FavoriteViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var favBinding: ActivityMainBinding
    private lateinit var favViewModel: FavoriteViewModel
    private lateinit var favAdapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(favBinding.root)

        favAdapter = UserListAdapter()
        favAdapter.notifyDataSetChanged()

        favBinding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@MainActivity)
            rvFavorite.adapter = favAdapter
        }


        favViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favViewModel.setListFavUser(this)
        favViewModel.getListFavUser().observe(this, {
            if (it != null) {
                favAdapter.setterList(it)
            }
        })
    }

}