package com.cupsofcode.recomposition_examples

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cupsofcode.recomposition_examples.ui.theme.Gray200
import kotlinx.coroutines.delay

/**
 * tip #3. Read the state value at the lowest composable function.
 *
 * By deferring the state read all the way down to the actual consumer(child node) of the state,
 * Runtime will recompose only that node, and not the parent and subsequent parents of it.
 *
 * P.S. "state read" or "read the state value" terms refer to the moment, when `getValue()`
 * from State<out T> interface is called. Example:
val isCompose = remember {
mutableStateOf(false)
}

isCompose.value <-state read
 *
 */


@Composable
fun Tip3() {
    val cardInfo = remember {
        mutableStateOf(CardInfo("Android", "View system"))
    }
    LaunchedEffect(key1 = cardInfo, block = {
        delay(2500)
        cardInfo.value = cardInfo.value.copy(secondaryText = "Jetpack Compose")
    })

    GoodGreetingsTip3 { cardInfo.value }
    // BadGreetingsTip3(cardInfo.value.secondaryText)
}

/**
 * 👍 : only inner column and its children get recomposed, because the read happens
 * in one of the children.
 */
@Composable
fun GoodGreetingsTip3(cardInfo: () -> CardInfo) {
    Column(
        modifier = Modifier
            .padding(all = 64.dp)
            .recomposeHighlighter()
            .recompositionCounter("column"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UpperRow()
        GoodRow(cardInfo = cardInfo)
    }
}

@Composable
private fun GoodRow(cardInfo: () -> CardInfo) {
    Row(
        modifier = Modifier
            .background(color = Gray200, shape = RoundedCornerShape(25.dp))
            .padding(all = 64.dp)
            .recomposeHighlighter()
            .recompositionCounter("lower row"),
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
        GoodInnerColumn(cardInfo = cardInfo)

    }
}

@Composable
private fun GoodInnerColumn(cardInfo: () -> CardInfo) {
    Column(
        modifier = Modifier
            .recompositionCounter("inner column")
            .recomposeHighlighter()
    ) {
        Text(
            text = "Android",
            color = Color.White,
            modifier = Modifier
                .recomposeHighlighter()
                .recompositionCounter("text1"),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = cardInfo().secondaryText,
            color = Color.White,
            modifier = Modifier
                .recomposeHighlighter()
                .recompositionCounter("text2"),
            fontSize = 16.sp
        )

    }
}

/**
 * 👎 : everything including the top column gets recomposed when the parameter's (secondaryText)
 * value changes
 */
@Composable
fun BadGreetingsTip3(secondaryText: String) {
    Column(
        modifier = Modifier
            .padding(all = 64.dp)
            .recomposeHighlighter()
            .recompositionCounter("column"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UpperRow()
        BadRow(secondaryText = secondaryText)
    }
}

@Composable
private fun BadRow(secondaryText: String) {
    Row(
        modifier = Modifier
            .background(color = Gray200, shape = RoundedCornerShape(25.dp))
            .padding(all = 64.dp)
            .recomposeHighlighter()
            .recompositionCounter("lower row"),
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
        BadInnerColumn(secondaryText = secondaryText)

    }
}

@Composable
private fun BadInnerColumn(secondaryText: String) {
    Column(
        modifier = Modifier
            .recompositionCounter("inner column")
            .recomposeHighlighter()
    ) {
        Text(
            text = "Android",
            color = Color.White,
            modifier = Modifier
                .recomposeHighlighter()
                .recompositionCounter("text1"),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = secondaryText,
            color = Color.White,
            modifier = Modifier
                .recomposeHighlighter()
                .recompositionCounter("text2"),
            fontSize = 16.sp
        )

    }
}

@Composable
private fun UpperRow() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .recompositionCounter("upperRow")
    ) {
        Text(
            text = "Initial release date:",
            modifier = Modifier.recompositionCounter("upperRow text1"),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "September 23, 2008",
            modifier = Modifier.recompositionCounter("upperRow text2"),
        )
    }
}

data class CardInfo(val primaryText: String, val secondaryText: String)