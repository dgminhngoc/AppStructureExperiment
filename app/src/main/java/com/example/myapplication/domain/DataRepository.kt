package com.example.myapplication.domain

import com.example.myapplication.models.*
import com.google.gson.JsonObject
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

interface IDataRepository {
    suspend fun fetchNews() : RequestResult<List<News>>
    suspend fun fetchVideos(videoId: String) : RequestResult<List<Video>>
    suspend fun authenticate(username: String, password: String): RequestResult<User>
    suspend fun refreshAccessToken(user: User): RequestResult<User>
}

class DataRepository() : IDataRepository {
    companion object{
        private const val SERVER_URL = "https://example.com"
        private const val API_LOGIN = "/api/login"
        private const val API_ACCESS_TOKEN = "/oauth/access_token"
        private const val API_NEWS = "/api/news"
        private const val API_VIDEOS = "/api/videos"
    }

    private var user: User? = null

    override suspend fun fetchNews(): RequestResult<List<News>> {
        var errorCode: Int? = null
        var exception: Exception? = null

        try {
            val response = HttpClient().get("$SERVER_URL$API_NEWS"){
                headers {
                    append(
                        name = "Authorization",
                        value = "bearer ${user?.accessToken}"
                    )
                    append(
                        name = "Accept-Version",
                        value = "1.0.0"
                    )
                }
            }

            when(response.status) {
                HttpStatusCode.OK -> {
                    return RequestResult.OnSuccess(listOf())
                }
                else -> {
                    errorCode = response.status.value
                }
            }
        }
        catch (e: Exception) {
            exception = e
        }

        return RequestResult.OnError(
            errorCode = errorCode,
            exception = exception,
        )
    }

    override suspend fun fetchVideos(videoId: String): RequestResult<List<Video>> {
        var errorCode: Int? = null
        var exception: Exception? = null

        try {
            val response = HttpClient().get("$SERVER_URL$API_VIDEOS"){
                headers {
                    append(
                        name = "Authorization",
                        value = "bearer ${user?.accessToken}"
                    )
                    append(
                        name = "Accept-Version",
                        value = "1.0.0"
                    )
                }
            }

            when(response.status) {
                HttpStatusCode.OK -> {
                    return RequestResult.OnSuccess(listOf())
                }
                else -> {
                    errorCode = response.status.value
                }
            }
        }
        catch (e: Exception) {
            exception = e
        }

        return RequestResult.OnError(
            errorCode = errorCode,
            exception = exception,
        )
    }

    override suspend fun authenticate(username: String, password: String): RequestResult<User> {
        var errorCode: Int? = null
        var exception: Exception? = null

        try {
            val response = HttpClient().post("$SERVER_URL$API_LOGIN"){
                headers {
                    append(
                        name = "Content-Type",
                        value = "application/json"
                    )
                    append(
                        name = "Accept-Version",
                        value = "1.0.0"
                    )
                }
                setBody(
                    body = JsonObject().apply {
                        addProperty("username", username)
                        addProperty("password", password)
                    }.asString
                )
            }

            when(response.status) {
                HttpStatusCode.OK -> {
                    user = User(
                        id = 0,
                        email = "tim.nguyen@solunar.de",
                        firstName = "Tim",
                        lastName = "Nguyen",
                        accessToken = "access_token",
                        refreshToken = "refresh_token",
                        userRole = "user"
                    )
                    return RequestResult.OnSuccess(data = user)
                }
                else -> {
                    errorCode = response.status.value
                }
            }
        }
        catch (e: Exception) {
            exception = e
        }

        return RequestResult.OnError(
            errorCode = errorCode,
            exception = exception,
        )
    }

    override suspend fun refreshAccessToken(user: User): RequestResult<User> {
        var errorCode: Int? = null
        var exception: Exception? = null
        try {
            val response = HttpClient().post("$SERVER_URL$API_ACCESS_TOKEN"){
                headers {
                    append(
                        name = "Content-Type",
                        value = "application/x-www-form-urlencoded"
                    )
                    append(
                        name = "Accept-Version",
                        value = "1.0.0"
                    )
                }
                setBody(
                    body = "grant_type=refresh_token&refresh_token=${user.refreshToken}"
                )
            }

            when(response.status) {
                HttpStatusCode.OK -> {
                    val newUser = User(
                        id = 0,
                        email = "tim.nguyen@solunar.de",
                        firstName = "Tim",
                        lastName = "Nguyen",
                        accessToken = "access_token",
                        refreshToken = "refresh_token",
                        userRole = "user"
                    )
                    return RequestResult.OnSuccess(data = newUser)
                }
                else -> {
                    errorCode = response.status.value
                }
            }
        }
        catch (e: Exception) {
            exception = e
        }

        return RequestResult.OnError(
            errorCode = errorCode,
            exception = exception,
        )
    }
}