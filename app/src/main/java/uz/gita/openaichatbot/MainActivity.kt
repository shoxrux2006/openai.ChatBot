package uz.gita.openaichatbot

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.openaichatbot.navigation.NavigationHandler
import uz.gita.openaichatbot.presentation.main.MainScreen
import uz.gita.openaichatbot.ui.theme.OpenaiChatBotTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationHandler: NavigationHandler

    @SuppressLint("CoroutineCreationDuringComposition", "FlowOperatorInvokedInComposition")
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenaiChatBotTheme() {
                Navigator(screen = MainScreen(), onBackPressed = {
                    true
                }) { navigator ->
                    navigationHandler.navStack.onEach {
                        it.invoke(navigator)
                    }.launchIn(lifecycleScope)
                    SlideTransition(navigator)
                }
            }
        }
    }
}