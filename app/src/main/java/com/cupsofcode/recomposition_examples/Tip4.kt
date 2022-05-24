package com.cupsofcode.recomposition_examples

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cupsofcode.recomposition_examples.ui.theme.Gray200

/**
 * tip 4: use Modifier lambdas for every frequent changing state (ex: animations, scrolling, etc).
 *
 * This is similar to tip #3 about deferring the state read, but needs a special highlighting,
 * because many devs might trip on it.
 *
 * When a state's value is passed to Modifier's regular function, the read happens right at that moment,
 * therefore causing the nearest scope to recompose. When the state's value passed to Modifier's
 * lambda function, the read is deferred until the Compose reaches the Layout or Drawing phases(
 * based on the Modifier type).
 *
 */
@Composable
fun Tip4() {
    val infiniteTransition = rememberInfiniteTransition()
    val yAxis = infiniteTransition.animateFloat(
        initialValue = -100f,
        targetValue = 350f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier
            .padding(all = 64.dp)
            .recomposeHighlighter()
            .recompositionCounter("column"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GoodGreetingsTip4 { yAxis.value.toInt() }
        // BadGreetingsTip4 { yAxis.value.toInt()}
    }
}

/**
 * 👍 : With the receiver lambda function (Modifier.offset{}) in place, this composable function
 * doesn't get recomposed at all.
 */
@Composable
private fun GoodGreetingsTip4(yAxis: () -> Int) {
    Row(
        modifier = Modifier
            .offset {
                IntOffset(y = yAxis(), x = 0)
            }
            .background(color = Gray200, shape = RoundedCornerShape(25.dp))
            .padding(all = 64.dp)
            .recomposeHighlighter()
            .recompositionCounter("box"),
        verticalAlignment = Alignment.Bottom
    ) {
    }
    Image(
        painter = painterResource(id = R.drawable.ic_android_green_24dp),
        contentDescription = "android logo",
        modifier = Modifier
            .size(48.dp)
            .recomposeHighlighter()
            .recompositionCounter("image"),
    )
    Text(
        text = "Android",
        color = Color.White,
        modifier = Modifier
            .recomposeHighlighter()
            .recompositionCounter("text"),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}

/**
 * 👎 : Even though we're passing the lambda of the state, the receiver (Modifier.offset())
 * itself reads right there, thus causing the nearest scope(Row) and its children to recompose.
 */
@Composable
private fun BadGreetingsTip4(yAxis: () -> Int) {
    Row(
        modifier = Modifier
            .offset(y = Dp(yAxis().toFloat()))
            .background(color = Gray200, shape = RoundedCornerShape(25.dp))
            .padding(all = 64.dp)
            .recomposeHighlighter()
            .recompositionCounter("box"),
        verticalAlignment = Alignment.Bottom
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_android_green_24dp),
            contentDescription = "android logo",
            modifier = Modifier
                .size(48.dp)
                .recomposeHighlighter()
                .recompositionCounter("image"),
        )
        Text(
            text = "Android",
            color = Color.White,
            modifier = Modifier
                .recomposeHighlighter()
                .recompositionCounter("text"),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

