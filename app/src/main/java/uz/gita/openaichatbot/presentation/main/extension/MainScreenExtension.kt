package uz.gita.openaichatbot.presentation.main.extension

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import uz.gita.openaichatbot.data.ChatData
import uz.gita.openaichatbot.ui.theme.OpenaiChatBotTheme
import uz.gita.openaichatbot.utils.Const


@Composable
fun ChatItem(modifier: Modifier,isLoading: Boolean, chatData: ChatData, isAnimate: Boolean) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        if (!chatData.isUser) {
            Row(modifier = Modifier.weight(2f)) {
                Column(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topEnd = 10.dp,
                                topStart = 10.dp,
                                bottomEnd = 10.dp,
                                bottomStart = 0.dp
                            )
                        )
                        .background(
                            Color.Gray
//                    MaterialTheme.colorScheme.surface
                        )
                        .padding(vertical = 5.dp, horizontal = 15.dp)
                ) {

                    Text(
                        text = if (!chatData.isUser) Const.botName else "",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (isLoading)
                        LoadingAnimation(modifier = Modifier.padding(10.dp))
                    else {
//                        var text by rememberSaveable {
//                            mutableStateOf("")
//                        }
//                        if (isAnimate) {
//                            LaunchedEffect(key1 = chatData.text) {
//                                chatData.text.forEach {
//                                    delay(100)
//                                    text += it
//                                }
//                            }
//                        } else {
//                            text = chatData.text
//                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = chatData.text,
                            color = MaterialTheme.colorScheme.tertiary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.weight(1f))
        } else {
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.weight(1f)) {
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topEnd = 10.dp,
                                topStart = 10.dp,
                                bottomEnd = 0.dp,
                                bottomStart = 10.dp
                            )
                        )
                        .background(
//                        Color.Gray
                            MaterialTheme.colorScheme.primary
                        )
                        .padding(vertical = 5.dp, horizontal = 15.dp)
                ) {
                    Text(
                        text = chatData.text,
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}


@Composable
private fun LoadingAnimation(
    modifier: Modifier = Modifier,
    circleSize: Dp = 8.dp,
    circleColor: Color = MaterialTheme.colorScheme.primary,
    spaceBetween: Dp = 5.dp,
    travelDistance: Dp = 15.dp
) {
    val circles = listOf(
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) }
    )

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 0f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 300 with LinearOutSlowInEasing
                        0.0f at 600 with LinearOutSlowInEasing
                        0.0f at 900 with LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current) { travelDistance.toPx() }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        circleValues.forEach { value ->
            Box(
                modifier = Modifier
                    .size(circleSize)
                    .graphicsLayer {
                        translationY = -value * distance
                    }
                    .background(
                        color = circleColor,
                        shape = CircleShape
                    )
            )
        }
    }

}

@Preview
@Composable
fun prev() {

}

