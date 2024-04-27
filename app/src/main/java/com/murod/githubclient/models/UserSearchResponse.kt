package com.murod.githubclient.models

data class UserSearchResponse(
    val incomplete_results: Boolean,
    val items: List<User>,
    val total_count: Int
)