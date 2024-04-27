package com.murod.githubclient.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.murod.githubclient.databinding.ActivityDownloadsBinding
import com.murod.githubclient.ui.adapters.RepoListAdapter
import com.murod.githubclient.viewmodels.RepoViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DownloadsActivity : AppCompatActivity() {

    private val binding: ActivityDownloadsBinding by lazy {
        ActivityDownloadsBinding.inflate(layoutInflater)
    }

    private lateinit var repoAdapter: RepoListAdapter
    private val viewModel: RepoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        repoAdapter = RepoListAdapter(
            isDownloadFolder = true,
            onDownloadClick = {

            },
            onRepoClick = {

            }
        )

        viewModel.getAllDownloadedRepos()
        lifecycleScope.launch {
            viewModel.repos.collect { repoList ->
                repoAdapter.submitList(repoList)
            }
        }

        binding.rvSavedRepos.apply {
            adapter = repoAdapter
            layoutManager = LinearLayoutManager(this@DownloadsActivity)
        }
    }
}