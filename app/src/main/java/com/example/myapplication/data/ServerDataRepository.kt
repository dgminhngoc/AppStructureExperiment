package com.example.myapplication.data

import com.example.myapplication.models.*
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*

interface IServerDataRepository {
    suspend fun fetchNews() : RequestResult<List<News>>
    suspend fun fetchVideos(videoId: String) : RequestResult<List<Video>>
    suspend fun authenticate(email: String, password: String): RequestResult<User>
    suspend fun resetPassword(email: String): RequestResult<String>
    suspend fun register(firstName: String, lastName: String,
                         email: String, password: String,
                         authority: String): RequestResult<String>
    suspend fun refreshAccessToken(refreshToken: String): RequestResult<User>
}

class ServerDataRepository(private val refreshToken: String? = null): IServerDataRepository {
    companion object API{
        private const val SERVER_URL = "https://example.com"
        private const val API_LOGIN = "/api/login"
        private const val API_REGISTER = "/api/user/registration"
        private const val API_RESET_PASSWORD = "/api/user/password/reset"
        private const val API_ACCESS_TOKEN = "/oauth/access_token"
        private const val API_NEWS = "/api/news"
        private const val API_VIDEOS = "/api/videos"

        private var accessToken: String? = null
        private val httpClient = HttpClient()
    }

    init {
        httpClient.plugin(HttpSend).intercept {request ->
            val originalCall = execute(request)
            if (originalCall.response.status == HttpStatusCode.Unauthorized) {
                refreshToken?.let {
                    refreshAccessToken(it)
                }
                execute(request)
            }
            else {
                originalCall
            }

        }
    }

    override suspend fun fetchNews(): RequestResult<List<News>> {
        var errorCode: Int? = null
        var exception: Exception? = null

        try {
            val response = HttpClient().get("$SERVER_URL$API_NEWS"){
                headers {
                    append(
                        name = "Authorization",
                        value = "bearer $accessToken"
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
                        value = "bearer $accessToken"
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

    override suspend fun authenticate(email: String, password: String): RequestResult<User> {
        var errorCode: Int? = null
        var exception: Exception? = null

//        try {
//            val response = HttpClient().post("$SERVER_URL$API_LOGIN"){
//                headers {
//                    append(
//                        name = "Content-Type",
//                        value = "application/json"
//                    )
//                    append(
//                        name = "Accept-Version",
//                        value = "1.0.0"
//                    )
//                }
//                setBody(
//                    body = JsonObject().apply {
//                        addProperty("username", email)
//                        addProperty("password", password)
//                    }.asString
//                )
//            }
//
//            when(response.status) {
//                HttpStatusCode.OK -> {
//                    user = User(
//                        id = 0,
//                        email = "tim.nguyen@solunar.de",
//                        firstName = "Tim",
//                        lastName = "Nguyen",
//                        accessToken = "access_token",
//                        refreshToken = "refresh_token",
//                        userRole = "ROLE_USER"
//                    )
//                    return RequestResult.OnSuccess(data = user)
//                }
//                else -> {
//                    errorCode = response.status.value
//                }
//            }
//        }
//        catch (e: Exception) {
//            exception = e
//        }

        val user = User(
            id = 0,
            email = "tim.nguyen@solunar.de",
            firstName = "Tim",
            lastName = "Nguyen",
            accessToken = "access_token",
            refreshToken = "refresh_token",
            userRole = "ROLE_USER"
        )
        return RequestResult.OnSuccess(data = user)
//        return RequestResult.OnError(
//            errorCode = errorCode,
//            exception = exception,
//        )
    }

    override suspend fun resetPassword(email: String): RequestResult<String> {
        var errorCode: Int? = null
        var exception: Exception? = null

//        try {
//            val response = HttpClient().post("$SERVER_URL$API_RESET_PASSWORD"){
//                headers {
//                    append(
//                        name = "Content-Type",
//                        value = "application/json"
//                    )
//                    append(
//                        name = "Accept-Version",
//                        value = "1.0.0"
//                    )
//                }
//                setBody(
//                    body = JsonObject().apply {
//                        addProperty("email", email)
//                    }.asString
//                )
//            }
//
//            when(response.status) {
//                HttpStatusCode.OK -> {
//                    return RequestResult.OnSuccess(data = response.body<String?>().toString())
//                }
//                else -> {
//                    errorCode = response.status.value
//                }
//            }
//        }
//        catch (e: Exception) {
//            exception = e
//        }
//
//        return RequestResult.OnError(
//            errorCode = errorCode,
//            exception = exception,
//        )
        return RequestResult.OnSuccess(data = "")
    }

    override suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        authority: String
    ): RequestResult<String> {
        var errorCode: Int? = null
        var exception: Exception? = null

//        try {
//            val response = HttpClient().post("$SERVER_URL$API_REGISTER"){
//                headers {
//                    append(
//                        name = "Content-Type",
//                        value = "application/json"
//                    )
//                    append(
//                        name = "Accept-Version",
//                        value = "1.0.0"
//                    )
//                }
//                setBody(
//                    body = JsonObject().apply {
//                        addProperty("firstName", firstName)
//                        addProperty("lastName", lastName)
//                        addProperty("email", email)
//                        addProperty("password", password)
//                        addProperty("authority", authority)
//                    }.asString
//                )
//            }
//
//            when(response.status) {
//                HttpStatusCode.OK -> {
//                    return RequestResult.OnSuccess(data = response.body<String?>().toString())
//                }
//                else -> {
//                    errorCode = response.status.value
//                }
//            }
//        }
//        catch (e: Exception) {
//            exception = e
//        }

        return RequestResult.OnSuccess(data = "")


//        return RequestResult.OnError(
//            errorCode = errorCode,
//            exception = exception,
//        )
    }

    override suspend fun refreshAccessToken(refreshToken: String): RequestResult<User> {
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
                    body = "grant_type=refresh_token&refresh_token=$refreshToken"
                )
            }

            when(response.status) {
                HttpStatusCode.OK -> {
                    val user = User(
                        id = 0,
                        email = "tim.nguyen@solunar.de",
                        firstName = "Tim",
                        lastName = "Nguyen",
                        accessToken = "access_token",
                        refreshToken = "refresh_token",
                        userRole = "ROLE_USER"
                    )
                    accessToken = user.accessToken
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
}