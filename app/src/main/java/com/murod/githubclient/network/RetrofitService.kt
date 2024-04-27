package com.murod.githubclient.network

import com.murod.githubclient.models.Repo
import com.murod.githubclient.models.UserSearchResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String
    ): UserSearchResponse

    @GET("users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String
    ): List<Repo>

    @GET("/repos/{owner}/{repo}/zipball")
    suspend fun downloadRepoArchive(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): Response<ResponseBody>

}
