package com.murod.githubclient.ui.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.murod.githubclient.R
import com.murod.githubclient.databinding.ActivityMainBinding
import com.murod.githubclient.models.Repo
import com.murod.githubclient.models.User
import com.murod.githubclient.ui.adapters.RepoListAdapter
import com.murod.githubclient.ui.adapters.UserListAdapter
import com.murod.githubclient.utils.gone
import com.murod.githubclient.utils.isInternetAvailable
import com.murod.githubclient.utils.navigateTo
import com.murod.githubclient.utils.openBrowser
import com.murod.githubclient.utils.showMessage
import com.murod.githubclient.utils.visible
import com.murod.githubclient.viewmodels.DownloadStatus
import com.murod.githubclient.viewmodels.RepoViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var keepSplashOnScreen = true
    private val delay = 500L
    private lateinit var userAdapter: UserListAdapter
    private lateinit var repoAdapter: RepoListAdapter
    private var originalUserList: List<User> = emptyList()
    private val viewModel: RepoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition{keepSplashOnScreen}
        Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay) //to keep splash screen longer
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (!isInternetAvailable()) {
            showMessage(getString(R.string.no_internet_warning))
        }

        userAdapter = UserListAdapter(this) { user ->
            getUserRepos(user)
        }

        repoAdapter = RepoListAdapter(
            isDownloadFolder = false,
            onDownloadClick = { repo ->
                downloadRepo(repo)
            },
            onRepoClick = { repo ->
                openBrowser(repo.html_url)
            }
        )

        binding.apply {
            rvUsers.adapter = userAdapter
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrBlank()) {
                        rvRepos.gone()
                        rvUsers.visible()
                        userAdapter.submitList(emptyList())
                    } else {
                        newText.let { query ->
                            viewModel.searchUsers(query)
                        }
                    }
                    return true
                }
            })

            lifecycleScope.launch {
                viewModel.users.collect { userList ->
                    userAdapter.submitList(userList)
                }
            }

            lifecycleScope.launch {
                viewModel.repos.collect { repoList ->
                    repoAdapter.submitList(repoList)
                }
            }

            btnDownloads.setOnClickListener {
                navigateTo(DownloadsActivity::class.java)
            }
        }
    }

    private fun downloadRepo(repo: Repo) {
        if (repo.downloadStatus == DownloadStatus.COMPLETED) {
            showMessage(getString(R.string.repository_is_already_downloaded))
        } else if (repo.downloadStatus == DownloadStatus.DOWNLOADING) {
            showMessage(getString(R.string.downloadind_is_already_started))
        } else {
            viewModel.downloadRepoArchive(repo.owner.login, repo.name)
            lifecycleScope.launch {
                viewModel.downloadStatus.collect { status ->
                    when (status) {
                        DownloadStatus.DOWNLOADING -> {
                            repo.downloadStatus = DownloadStatus.DOWNLOADING
                        }
                        DownloadStatus.COMPLETED -> {
                            repo.downloadStatus = DownloadStatus.COMPLETED
                            viewModel.insertRepo(repo)
                            showMessage(getString(R.string.download_is_finished))
                        }
                        DownloadStatus.FAILED -> {
                            repo.downloadStatus = DownloadStatus.FAILED
                            showMessage(getString(R.string.download_is_failed))
                        }
                        DownloadStatus.INITIAL -> {}
                    }
                    repoAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun getUserRepos(user: User) {
        binding.apply {
            rvUsers.gone()
            rvRepos.visible()
            rvRepos.adapter = repoAdapter
            rvRepos.layoutManager = LinearLayoutManager(this@MainActivity)
            viewModel.getUserRepos(user.login)
        }
    }
}