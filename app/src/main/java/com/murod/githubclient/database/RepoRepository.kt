package com.murod.githubclient.database

import android.os.Environment
import com.murod.githubclient.models.Repo
import com.murod.githubclient.utils.logError
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class RepoRepository (private val repoDao: RepoDao) {

    suspend fun getAll(): MutableList<Repo> {
        return repoDao.getAll()
    }

    suspend fun insert(repo: Repo) {
        repoDao.insert(repo)
    }

    fun saveResponseBodyToFile(
        body: ResponseBody?,
        repoName: String,
        onComplete: (success: Boolean) -> Unit
    ) {
        body?.let { responseBody ->
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, "$repoName.zip")
            try {
                FileOutputStream(file).use { outputStream ->
                    outputStream.write(responseBody.bytes())
                    onComplete(true)
                }
            } catch (e: IOException) {
                logError(e.message)
                onComplete(false)
            }
        } ?: run {
            onComplete(false)
        }
    }
}