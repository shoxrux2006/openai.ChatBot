package uz.gita.openaichatbot.remote.data.request

import com.google.gson.annotations.SerializedName

data class ChatRequest(

	@field:SerializedName("temperature")
	val temperature: Int? = null,

	@field:SerializedName("messages")
	val messages: List<MessagesItem?>? = null,

	@field:SerializedName("model")
	val model: String? = null
)

data class MessagesItem(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("content")
	val content: String? = null
)
