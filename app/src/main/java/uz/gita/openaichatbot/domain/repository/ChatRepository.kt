package uz.gita.bellissimo_clone.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.bellissimo_clone.utils.NetworkResponse
import uz.gita.openaichatbot.remote.data.response.ChatResponse

interface ChatRepository {
    fun chat(input: String): Flow<NetworkResponse<ChatResponse>>
}