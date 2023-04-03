package uz.gita.openaichatbot.presentation.main.vm.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.bellissimo_clone.domain.repository.ChatRepository
import uz.gita.bellissimo_clone.utils.NetworkResponse
import uz.gita.openaichatbot.data.ChatData
import uz.gita.openaichatbot.navigation.AppNavigation
import uz.gita.openaichatbot.presentation.main.vm.MainIntent
import uz.gita.openaichatbot.presentation.main.vm.MainSideEffect
import uz.gita.openaichatbot.presentation.main.vm.MainUIState
import uz.gita.openaichatbot.presentation.main.vm.MainViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val repository: ChatRepository,
    private val navigation: AppNavigation
) : ViewModel(),
    MainViewModel {
    private var chatList: MutableList<ChatData> = arrayListOf()
    override fun onEventDispatcher(intent: MainIntent) = intent {
        when (intent) {
            is MainIntent.SubmitButton -> {
                if (intent.inputText.isNotEmpty()) {
                    chatList.add(ChatData(intent.inputText, isUser = true))
                    chatList.add(ChatData("", isUser = false))
                    reduce { MainUIState.Success(chatList) }
                    delay(500)
                    reduce { MainUIState.Loading(true) }
                    chat(intent.inputText)
                } else {
                    postSideEffect(MainSideEffect.Message("answer is empty"))
                }
            }
        }
    }

    override val container: Container<MainUIState, MainSideEffect> =
        container(MainUIState.Success(chatList))


    private fun chat(input: String) = intent {
        viewModelScope.launch {
            repository.chat(input).collect {
                when (it) {
                    is NetworkResponse.Error -> {
                        postSideEffect(MainSideEffect.Message(it.message))
                    }
                    is NetworkResponse.Loading -> {
                        reduce { MainUIState.Loading(it.isLoading) }
                    }
                    is NetworkResponse.NoConnection -> {
                        postSideEffect(MainSideEffect.Message("No internet Connection"))
                    }
                    is NetworkResponse.Success -> {
                        it.data?.let { its ->
                            if (its.choices!!.isNotEmpty() && its.choices[0]?.message?.content != null) {
                                chatList[chatList.lastIndex] =
                                    ChatData(
                                        its.choices[0]!!.message!!.content!!.trim(),
                                        isUser = false,
                                    )

                            }
                        }
                        reduce { MainUIState.Success(chatList) }
                    }
                }
            }
        }
    }


}