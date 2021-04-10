package com.dapoidev.ourgithub.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dapoidev.ourgithub.R
import com.dapoidev.ourgithub.databinding.FragmentFollowingBinding
import com.dapoidev.ourgithub.view.UserDetailActivity
import com.dapoidev.ourgithub.view.adapter.UserListAdapter
import com.dapoidev.ourgithub.viewModel.FollowingViewModel

@Suppress("DEPRECATION")
class FollowingFragment : Fragment(R.layout.fragment_following) {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var githubUsername: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // menerimma data yang dikirim dari MainActivity
        githubUsername = arguments?.getString(UserDetailActivity.USERNAME_PACKAGE).toString()

        _binding = FragmentFollowingBinding.bind(view)

        userListAdapter = UserListAdapter()
        userListAdapter.notifyDataSetChanged()

        binding.rvGithubFollowing.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = userListAdapter
        }

        showData(true)

        followingViewModel = ViewModelProvider(this, ViewModelProvider
            .NewInstanceFactory())
            .get(FollowingViewModel::class.java)
        followingViewModel.setListFollowing(githubUsername)
        followingViewModel.getListFollowing().observe(viewLifecycleOwner, {
            if (it != null) {
                userListAdapter.setterList(it)
                showData(false)
            }
        })
    }

    private fun showData(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}