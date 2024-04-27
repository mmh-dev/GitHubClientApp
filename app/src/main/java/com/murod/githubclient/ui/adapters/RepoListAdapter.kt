package com.murod.githubclient.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.murod.githubclient.databinding.ItemRepoBinding
import com.murod.githubclient.models.Repo
import com.murod.githubclient.utils.gone
import com.murod.githubclient.utils.visible
import com.murod.githubclient.viewmodels.DownloadStatus

class RepoListAdapter (val isDownloadFolder: Boolean, val onRepoClick: (Repo) -> Unit, private val onDownloadClick: (Repo) ->
Unit) :
    ListAdapter<Repo,
        RepoListAdapter.RepoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = getItem(position)
        holder.bind(repo)
    }

    inner class RepoViewHolder(private val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Repo) {
            with(binding){
                tvRepoName.text = repo.name
                tvRepoOwner.text = repo.owner.login
                when (repo.downloadStatus) {
                    DownloadStatus.COMPLETED -> {
                        progressBar.gone()
                        if (isDownloadFolder) {
                            btnDownloadRepo.gone()
                        } else {
                            btnDownloadRepo.visible()
                        }
                        btnDownloadRepo.setColorFilter(Color.GREEN)
                    }
                    DownloadStatus.DOWNLOADING -> {
                        btnDownloadRepo.gone()
                        progressBar.visible()
                    }
                    DownloadStatus.FAILED -> {
                        btnDownloadRepo.setColorFilter(Color.RED)
                    }
                    DownloadStatus.INITIAL -> {}
                }
                root.setOnClickListener {
                    onRepoClick(repo)
                }

                btnDownloadRepo.setOnClickListener {
                    onDownloadClick(repo)
                }
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem == newItem
        }
    }
}
