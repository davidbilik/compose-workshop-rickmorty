package cz.ackee.ackeecompose.ui.detail

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.google.accompanist.coil.CoilImage
import cz.ackee.ackeecompose.domain.Character
import cz.ackee.ackeecompose.ui.base.findNavController
import cz.ackee.ackeecompose.ui.theme.toolbarGray
import kotlin.math.absoluteValue

@Composable
fun CharacterDetail(character: Character) {
    var offset by remember { mutableStateOf(0f) }
    var heroHeight by remember {
        mutableStateOf(0)
    }
    val appbarHeight = with(LocalDensity.current) {
        56.dp.toPx()
    }
    Scaffold(
        topBar = {
            val navController = findNavController()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged {
                        heroHeight = it.height
                    }
                    .aspectRatio(1.25f)
            ) {
                CoilImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset {
                            IntOffset(0, (offset / 3).toInt())
                        },
                    fadeIn = true,
                    data = character.image, contentDescription = character.name,
                    contentScale = ContentScale.Crop,

                    )

                Box(
                    modifier = Modifier
                        .background(
                            Brush.linearGradient(
                                0f to Color.Black.copy(alpha = 0.6f),
                                0.5f to Color.Transparent,
                            )
                        )
                        .size(96.dp)
                )
                val dragProgress = offset.absoluteValue / (heroHeight - appbarHeight)
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigateUp()
                            },
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "back",
                                tint = Color.White
                            )
                        }
                    },
                    title = { },
                    backgroundColor = toolbarGray.copy(alpha = dragProgress),
                    elevation = lerp(start = 0.dp, stop = 0.dp, fraction = dragProgress)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                0.8f to Color.Transparent,
                                1f to Color.Black.copy(alpha = 0.6f)
                            )
                        )
                )
                Text(
                    text = character.name,
                    fontSize = lerp(start = 25.sp, stop = 18.sp, fraction = dragProgress),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .offset {
                            IntOffset((dragProgress * 96).toInt(), offset.toInt())
                        }
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }
        }
    ) {

        val dragState = rememberDraggableState {
            offset = (offset + it).coerceIn(-(heroHeight - appbarHeight).toFloat(), 0f)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(x = 0, y = offset.toInt())
                }
                .background(MaterialTheme.colors.background)
                .zIndex(100f)
                .draggable(dragState, orientation = Orientation.Vertical),
        ) {
            val items = listOf(
                "Location" to character.location,
                "Origin" to character.origin,
                "Species" to character.species,
                "Gender" to character.gender.toString(),
                "Status" to character.status.toString(),
            )
            items.forEachIndexed { index, (key, value) ->
                InfoRow(key, value, index * 100)
            }
        }
    }
}

@Composable
fun InfoRow(
    key: String,
    value: String,
    delay: Int
) {
    val alpha = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        alpha.animateTo(1f, animationSpec = tween(durationMillis = 1000, delayMillis = delay, easing = FastOutSlowInEasing))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(alpha = alpha.value)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = key,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold
        )
        Text(value)
    }
}


