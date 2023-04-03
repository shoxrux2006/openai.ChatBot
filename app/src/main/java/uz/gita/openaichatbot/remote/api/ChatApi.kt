package uz.gita.openaichatbot.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.gita.openaichatbot.remote.data.request.ChatRequest
import uz.gita.openaichatbot.remote.data.response.ChatResponse

interface ChatApi {
    @POST("chat/completions")
    suspend fun chat(
        @Body chatRequest: ChatRequest
    ): Response<ChatResponse>
}