package com.murod.githubclient.database

import androidx.room.*
import com.murod.githubclient.models.Repo


@Dao
interface RepoDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(repo: Repo)

    @Query("SELECT * FROM REPOS")
    suspend fun getAll(): MutableList<Repo>
}