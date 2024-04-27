package com.murod.githubclient.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.murod.githubclient.models.Repo
import com.murod.githubclient.typeconverters.DownloadStatusConverter
import com.murod.githubclient.typeconverters.OwnerTypeConverter

@Database(entities = [Repo::class], version = 1)
@TypeConverters(OwnerTypeConverter::class, DownloadStatusConverter::class)
abstract class RepoDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}