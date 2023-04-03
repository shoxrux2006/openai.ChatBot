package uz.gita.openaichatbot.domain.repository.impl

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.banking.data.shp.Shp
import uz.gita.bellissimo_clone.domain.repository.ChatRepository
import uz.gita.bellissimo_clone.utils.NetworkResponse
import uz.gita.openaichatbot.remote.api.ChatApi
import uz.gita.openaichatbot.remote.data.request.ChatRequest
import uz.gita.openaichatbot.remote.data.request.MessagesItem
import uz.gita.openaichatbot.remote.data.response.ChatResponse
import uz.gita.openaichatbot.utils.Const
import uz.gita.openaichatbot.utils.hasConnection
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val shp: Shp,
    private val chatApi: ChatApi,
    @ApplicationContext private val context: Context
) : ChatRepository {
    private val chatList = arrayListOf<MessagesItem>()
    override fun chat(input: String): Flow<NetworkResponse<ChatResponse>> =
        flow<NetworkResponse<ChatResponse>> {
            if (hasConnection(context)) {
                    chatList.add(MessagesItem(role = Const.userRole, input))
                val request = chatApi.chat(
                    ChatRequest(
                        model = Const.gptModel,
                        temperature = Const.temperature,
                        messages = chatList
                    )
                )
                if (request.isSuccessful) {
                    if (request.body() != null) {
                            request.body()?.choices?.get(0)?.message?.let {
                                chatList.add(MessagesItem(it.role, it.content))
                            }
                        emit(NetworkResponse.Loading(false))
                        emit(NetworkResponse.Success(request.body()!!))
                    }
                } else {
                    emit(NetworkResponse.Loading(false))
                    emit(NetworkResponse.Error(request.message()))
                }
            } else {
                emit(NetworkResponse.NoConnection())
            }
        }.flowOn(Dispatchers.IO)


}