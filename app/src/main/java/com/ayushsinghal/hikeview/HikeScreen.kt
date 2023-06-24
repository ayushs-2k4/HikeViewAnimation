package com.ayushsinghal.hikeview

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun HikeScreen(
    title: String,
    distance: String,
    thumbNail:Int,
    elevationData: List<Pair<Int, Int>>,
    heartRateData: List<Pair<Int, Int>>,
    paceData: List<Pair<Int, Int>>
) {

    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    val rotateValue by animateFloatAsState(targetValue = if (isExpanded) 90f else 0f, label = "")

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 10.dp)
    )
    {
        Row(
            modifier = Modifier
                .offset(y = 30.dp)
                .padding(horizontal = 10.dp)
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { isExpanded = !isExpanded }
                )
        ) {
            Image(
                painter = painterResource(thumbNail),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(text = distance)
            }

            IconButton(
                onClick = { isExpanded = !isExpanded },
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(rotateValue)
                )
            }
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }),
            exit = scaleOut(
                transformOrigin = TransformOrigin(
                    pivotFractionX = 0.5f,
                    pivotFractionY = 0.5f
                )
            )
        ) {
            DataScreen(
                elevationData = elevationData,
                heartRateData = heartRateData,
                paceData = paceData
            )
        }
    }

}

@Composable
fun DataScreen(
    modifier: Modifier = Modifier,
    elevationData: List<Pair<Int, Int>>,
    heartRateData: List<Pair<Int, Int>>,
    paceData: List<Pair<Int, Int>>
) {

    var selectedButton by rememberSaveable { mutableStateOf("Elevation") }

    Column() {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .size(width = 300.dp, height = 300.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                val selectedData = when (selectedButton) {
                    "Elevation" -> elevationData
                    "Heart Rate" -> heartRateData
                    "Pace" -> paceData
                    else -> emptyList()
                }

                val selectedColor = when (selectedButton) {
                    "Elevation" -> Color(0xFF8E8D92)
                    "Heart Rate" -> Color(0xFFB35959)
                    "Pace" -> Color(0xFF796AB4)
                    else -> Color.Green
                }

                selectedData.forEachIndexed { index, (height, horizontalOffset) ->
                    val animatedHeight by animateIntAsState(
                        selectedData[index].first,
                        animationSpec =
//                        spring(
//                            dampingRatio = Spring.DampingRatioMediumBouncy,
//                            stiffness = Spring.StiffnessMediumLow
//                        )
                        tween(delayMillis = 100 + index * 20,
                            easing = { OvershootInterpolator(3F).getInterpolation(it) }),
                        label = ""
                    )

                    val animatedOffset by animateIntAsState(
                        selectedData[index].second,
                        animationSpec =
//                        spring(
//                            dampingRatio = Spring.DampingRatioMediumBouncy,
//                            stiffness = Spring.StiffnessMediumLow
//                        )
                        tween(delayMillis = 100 + index * 20,
                            easing = { OvershootInterpolator(2F).getInterpolation(it) }), label = ""
                    )
                    val animatedColor by animateColorAsState(
                        targetValue = selectedColor,
                        label = ""
                    )

                    Capsule(
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .offset(
                                x = (0).dp,
                                y = animatedOffset.dp
                            ),
                        color = animatedColor, width = 25.dp, height = animatedHeight.dp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme)
            {
                TextButton(
                    onClick = {
                        selectedButton = "Elevation"
                    },
                    modifier = Modifier.padding(16.dp),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (selectedButton == "Elevation") {
                            Color.Gray
                        } else {
//                        MaterialTheme.colorScheme.primary
                            Color(0XFF007AFF)
                        }
                    )
                ) {
                    Text("Elevation")
                }
            }

            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme)
            {
                TextButton(
                    onClick = {
                        selectedButton = "Heart Rate"
                    },
                    modifier = Modifier.padding(16.dp),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (selectedButton == "Heart Rate") {
                            Color.Gray
                        } else {
//                        MaterialTheme.colorScheme.primary
                            Color(0XFF007AFF)
                        }
                    )
                ) {
                    Text("Heart Rate")
                }
            }


            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme)
            {
                TextButton(
                    onClick = {
                        selectedButton = "Pace"
                    },
                    modifier = Modifier.padding(16.dp),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (selectedButton == "Pace") {
                            Color.Gray
                        } else {
//                        MaterialTheme.colorScheme.primary
                            Color(0XFF007AFF)
                        }
                    )
                ) {
                    Text("Pace")
                }
            }

        }
    }
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}

@Composable
fun Capsule(
    modifier: Modifier = Modifier,
    color: Color,
    width: Dp,
    height: Dp
) {
    Canvas(
        modifier = modifier.size(width = width, height = height),
        onDraw = {
            drawRoundRect(
                color = color,
                size = Size(width = width.toPx(), height = height.toPx()),
                cornerRadius = CornerRadius(360.dp.toPx(), 360.dp.toPx())
            )
        }
    )
}

//@Preview(showSystemUi = false)
//@Composable
//fun DataScreenPreview() {
//    val elevationData = listOf<Pair<Int, Int>>(
//        //height, offset
//        Pair(25, 300),
//        Pair(30, 290),
//        Pair(25, 290),
//        Pair(25, 270),
//        Pair(35, 240),
//        Pair(25, 245),
//        Pair(30, 260),
//        Pair(100, 190),
//        Pair(40, 150),
//        Pair(50, 160),
//        Pair(80, 200),
//        Pair(30, 300)
//    )
//
//    val heartRateData = listOf<Pair<Int, Int>>(
//        //height, offset
//        Pair(30, 280),
//        Pair(25, 290),
//        Pair(30, 270),
//        Pair(40, 240),
//        Pair(40, 210),
//        Pair(25, 220),
//        Pair(28, 240),
//        Pair(100, 150),
//        Pair(60, 110),
//        Pair(70, 170),
//        Pair(30, 230),
//        Pair(30, 250),
//    )
//
//    val paceData = listOf<Pair<Int, Int>>(
//        //height, offset
//        Pair(25, 50),
//        Pair(28, 70),
//        Pair(25, 70),
//        Pair(50, 70),
//        Pair(80, 70),
//        Pair(28, 45),
//        Pair(80, 60),
//        Pair(80, 180),
//        Pair(150, 70),
//        Pair(28, 70),
//        Pair(32, 73),
//        Pair(32, 75),
//    )
//
//    DataScreen(
//        modifier = Modifier,
//        elevationData = elevationData,
//        heartRateData = heartRateData,
//        paceData = paceData
//    )
//}

//@Preview(showSystemUi = true)
//@Composable
//fun CapsulePreview() {
//    Capsule(
//        color = Color.Gray,
//        width = 25.dp,
//        height = 50.dp
//    )
//}

@Preview(showSystemUi = true)
@Composable
fun HikeScreenPreview() {

    val elevationData = listOf<Pair<Int, Int>>(
        //height, offset
        Pair(25, 300),
        Pair(30, 290),
        Pair(25, 290),
        Pair(25, 270),
        Pair(35, 240),
        Pair(25, 245),
        Pair(30, 260),
        Pair(100, 190),
        Pair(40, 150),
        Pair(50, 160),
        Pair(80, 200),
        Pair(30, 300)
    )

    val heartRateData = listOf<Pair<Int, Int>>(
        //height, offset
        Pair(30, 280),
        Pair(25, 290),
        Pair(30, 270),
        Pair(40, 240),
        Pair(40, 210),
        Pair(25, 220),
        Pair(28, 240),
        Pair(100, 150),
        Pair(60, 110),
        Pair(70, 170),
        Pair(30, 230),
        Pair(30, 250),
    )

    val paceData = listOf<Pair<Int, Int>>(
        //height, offset
        Pair(25, 50),
        Pair(28, 70),
        Pair(25, 70),
        Pair(50, 70),
        Pair(80, 70),
        Pair(28, 45),
        Pair(80, 60),
        Pair(80, 180),
        Pair(150, 70),
        Pair(28, 70),
        Pair(32, 73),
        Pair(32, 75),
    )

    HikeScreen(
        title = "Lonesome Ridge Trail",
        distance = "4.5 km",
        thumbNail = R.drawable.small_image,
        elevationData = elevationData,
        heartRateData = heartRateData,
        paceData = paceData
    )
}