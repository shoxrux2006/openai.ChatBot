package uz.gita.openaichatbot.presentation.main.vm

import uz.gita.openaichatbot.data.ChatData
import uz.gita.openaichatbot.utils.AppViewModel


interface MainViewModel : AppViewModel<MainIntent, MainUIState, MainSideEffect> {

}

sealed interface MainIntent {
    data class SubmitButton(
        val inputText: String
    ) : MainIntent
}

sealed interface MainUIState {
    data class Success(
        val chatList: List<ChatData>
    ) : MainUIState

    data class Loading(val isLoad: Boolean = false) : MainUIState
    data class Error(
        val message: String
    ) : MainUIState

}

sealed interface MainSideEffect {
    data class Message(val text: String) : MainSideEffect
}