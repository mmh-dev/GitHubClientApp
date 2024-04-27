package com.murod.githubclient.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.murod.githubclient.viewmodels.DownloadStatus

@Entity (tableName = "repos")
data class Repo(
    @PrimaryKey
    val id: Int,
    val html_url: String,
    val name: String,
    val owner: Owner,
    var downloadStatus: DownloadStatus = DownloadStatus.INITIAL
)