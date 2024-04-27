package com.murod.githubclient.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murod.githubclient.database.RepoRepository
import com.murod.githubclient.models.Repo
import com.murod.githubclient.models.User
import com.murod.githubclient.network.RetrofitService
import com.murod.githubclient.utils.logError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class  RepoViewModel (private val retrofitService: RetrofitService, private val repository: RepoRepository) : ViewModel() {
    private val _repos = MutableStateFlow<List<Repo>>(emptyList())
    val repos: StateFlow<List<Repo>>
        get() = _repos

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>>
        get() = _users

    private val _downloadStatus = MutableStateFlow<DownloadStatus> (DownloadStatus.INITIAL)
    val downloadStatus: StateFlow<DownloadStatus>
        get() = _downloadStatus

    fun searchUsers(query: String) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                val response = retrofitService.searchUsers(query)
                _users.value = response.items
            } catch (e: Exception){
                logError(e.message)
            }
        }
    }

    fun getUserRepos(userName: String) {
        viewModelScope.launch ((Dispatchers.IO)) {
            try {
                val response = retrofitService.getUserRepos(userName)
                _repos.value = response
            } catch (e: Exception){
                logError(e.message)
            }
        }
    }

    fun downloadRepoArchive (owner: String, repoName: String) {
        viewModelScope.launch ((Dispatchers.IO)) {
            try {
                val response = retrofitService.downloadRepoArchive(owner, repoName)
                if (response.isSuccessful) {
                    _downloadStatus.value = DownloadStatus.DOWNLOADING
                    repository.saveResponseBodyToFile(
                        body = retrofitService.downloadRepoArchive(owner, repoName).body(),
                        repoName = repoName
                    ) { success ->
                        if (success) {
                            _downloadStatus.value = DownloadStatus.COMPLETED
                        } else {
                            _downloadStatus.value = DownloadStatus.FAILED
                        }
                    }
                }
            } catch (e: Exception){
                logError(e.message)
            }
        }
    }

    fun insertRepo(repo: Repo) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(repo)
    }

    fun getAllDownloadedRepos() {
        viewModelScope.launch(Dispatchers.IO) {
            _repos.value = repository.getAll()
        }
    }
}

sealed class DownloadStatus {
    object INITIAL : DownloadStatus()
    object DOWNLOADING : DownloadStatus()
    object COMPLETED : DownloadStatus()
    object FAILED : DownloadStatus()
}
