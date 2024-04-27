package com.murod.githubclient.typeconverters

import androidx.room.TypeConverter
import com.murod.githubclient.viewmodels.DownloadStatus

class DownloadStatusConverter {
    @TypeConverter
    fun fromDownloadStatus(status: DownloadStatus): String {
        return when (status) {
            DownloadStatus.INITIAL -> "INITIAL"
            DownloadStatus.DOWNLOADING -> "DOWNLOADING"
            DownloadStatus.COMPLETED -> "COMPLETED"
            DownloadStatus.FAILED -> "FAILED"
        }
    }

    @TypeConverter
    fun toDownloadStatus(status: String): DownloadStatus {
        return when (status) {
            "INITIAL" -> DownloadStatus.INITIAL
            "DOWNLOADING" -> DownloadStatus.DOWNLOADING
            "COMPLETED" -> DownloadStatus.COMPLETED
            "FAILED" -> DownloadStatus.FAILED
            else -> throw IllegalArgumentException("Unknown status: $status")
        }
    }
}
