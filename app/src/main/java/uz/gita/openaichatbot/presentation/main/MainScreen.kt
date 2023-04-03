package uz.gita.openaichatbot.presentation.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.openaichatbot.R
import uz.gita.openaichatbot.data.ChatData
import uz.gita.openaichatbot.presentation.main.extension.ChatItem
import uz.gita.openaichatbot.presentation.main.vm.MainIntent
import uz.gita.openaichatbot.presentation.main.vm.MainSideEffect
import uz.gita.openaichatbot.presentation.main.vm.MainUIState
import uz.gita.openaichatbot.presentation.main.vm.MainViewModel
import uz.gita.openaichatbot.presentation.main.vm.impl.MainViewModelImpl

class MainScreen : AndroidScreen() {
    @Composable
    override fun Content() {

        val context = LocalContext.current
        val viewModel: MainViewModel = getViewModel<MainViewModelImpl>()

        viewModel.collectSideEffect {
            when (it) {
                is MainSideEffect.Message -> {
                    Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
                }
            }
        }
        val uiState = viewModel.collectAsState().value
        MainScreenContent(
            uiState, viewModel::onEventDispatcher
        )
    }

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreenContent(
        uiState: MainUIState, onEventDispatcher: (MainIntent) -> Unit
    ) {
        var isLoad by remember {
            mutableStateOf(false)
        }
        var chatList by remember {
            mutableStateOf(listOf<ChatData>())
        }
        var editText by rememberSaveable {
            mutableStateOf("")
        }
        when (uiState) {
            is MainUIState.Error -> {

            }
            is MainUIState.Loading -> {
                isLoad = uiState.isLoad
            }
            is MainUIState.Success -> {
                isLoad = false
                Log.d("TTT", "size ${uiState.chatList.size}")
                chatList = listOf()
                chatList = uiState.chatList
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .paint(
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = R.drawable.background),
                    )
                    .padding(10.dp)
            ) {
                items(chatList.size) {
                    ChatItem(
                        modifier = Modifier.padding(vertical = 10.dp),
                        isLoading = isLoad && chatList.lastIndex == it,
                        chatData = chatList[it],
                        it == chatList.lastIndex
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .padding(5.dp),
                    enabled = !isLoad,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    value = editText,
                    onValueChange = { editText = it })
                Icon(
                    modifier = Modifier
                        .size(TextFieldDefaults.MinHeight)
                        .padding(2.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(10.dp)
                        .clickable {
                            if (!isLoad) {
                                onEventDispatcher(MainIntent.SubmitButton(editText))
                                editText = ""
                            }
                        },
                    imageVector = Icons.Default.Send,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "Send"
                )
            }
        }
    }


}