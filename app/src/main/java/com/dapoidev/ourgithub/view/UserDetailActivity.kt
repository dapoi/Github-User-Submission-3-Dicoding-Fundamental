package com.dapoidev.ourgithub.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dapoidev.ourgithub.R
import com.dapoidev.ourgithub.databinding.ActivityUserDetailBinding
import com.dapoidev.ourgithub.view.adapter.SectionPagerAdapter
import com.dapoidev.ourgithub.viewModel.UserDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailActivity : AppCompatActivity() {
    companion object {
        const val USERNAME_PACKAGE = "username"
        const val ID_PACKAGE = "id"
        const val AVA_PACKAGE = "avatar"
    }

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var detailViewModel: UserDetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // language setting
        val followers = resources.getString(R.string.followers)
        val following = resources.getString(R.string.following)
        val repo = resources.getString(R.string.repository)
        binding.apply {
            followersDetail.text = followers
            followingDetail.text = following
            repoDetail.text = repo
        }

        val gitUsername = intent.getStringExtra(USERNAME_PACKAGE)
        val gitId = intent.getIntExtra(ID_PACKAGE, 0)
        val gitAva = intent.getStringExtra(AVA_PACKAGE)

        val saveData = Bundle()
        saveData.putString(USERNAME_PACKAGE, gitUsername)

        // jangan lupa menghapus argumen ViewModelProvider.NewInstanceFactory, karena kita
        // tidak membutuhkannya akibat penggunaan AndroidViewModel
        detailViewModel = ViewModelProvider(this)
            .get(UserDetailViewModel::class.java)
        detailViewModel.setUserDetail(gitUsername.toString())
        detailViewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    nameDetail.text = it.name
                    usernameDetail.text = it.login
                    followersDetail.text = it.followers.toString()
                    followingDetail.text = it.following.toString()
                    repoDetail.text = it.public_repos.toString()
                    companyDetail.text = it.company
                    locationDetail.text = it.location
                    Glide.with(this@UserDetailActivity)
                        .load(it.avatar_url)
                        .into(imgDetail)
                }
            }
        })

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, saveData)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }

        var checkUserFav = false
        CoroutineScope(Dispatchers.IO).launch {
            val countFav = detailViewModel.checkUser(gitId)
            withContext(Dispatchers.Main) {
                if (countFav != null) {
                    if (countFav > 0) {
                        val buttonFav = binding.buttonFav
                        if (buttonFav != null) buttonFav.isChecked = true
                        checkUserFav = true
                    } else {
                        val buttonFav = binding.buttonFav
                        if (buttonFav != null) buttonFav.isChecked = false
                        checkUserFav = false
                    }
                }
            }
        }

        binding.buttonFav?.setOnClickListener {
            checkUserFav = !checkUserFav
            if (checkUserFav) {
                detailViewModel.addToFav(gitUsername.toString(), gitId, gitAva.toString())
                Toast.makeText(applicationContext,
                    "Add to Favorite", Toast.LENGTH_SHORT).show()
            } else {
                detailViewModel.removeFromFav(gitId)
                Toast.makeText(applicationContext,
                    "Remove from Favorite", Toast.LENGTH_SHORT).show()
                val buttonFav = binding.buttonFav
                if (buttonFav != null) buttonFav.isChecked = checkUserFav
            }
        }
    }
}
