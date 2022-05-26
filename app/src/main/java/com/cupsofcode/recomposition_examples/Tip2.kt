package com.cupsofcode.recomposition_examples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cupsofcode.recomposition_examples.ui.theme.*
import kotlinx.coroutines.delay

/**
 * tip #2. Use the key composable*
 *
 *  *Lazy layout have a built-in support for the key composable via items DSL.
 *
 * Runtime assigns a type(restartable, replaceable, movable) to every group in composition.
 * When the key is not set, runtime can't determine whether item in the list was changed
 * or it was moved. By setting the key, runtime lets compiler know about the unique
 * hashcode for each item, therefore prevents the recomposition of all children,
 * and recomposes only changed ones.
 */
@Composable
fun Tip2() {
    val initialList = arrayListOf<FruitRow>(
        FruitRow("Apple", Gray200),
        FruitRow("Orange", Brown200),
        FruitRow("Strawberry", Green700),
        FruitRow("Blueberry", Brown500),
        FruitRow("Banana", Green200)
    )
    val listState by remember {
        mutableStateOf(initialList)
    }
    var trigger by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = listState, block = {
        delay(900)
        initialList.removeAt(2)
        trigger = true
    })

    GoodGreetingsTip2(fruits = listState, isItTriggered = trigger)
    //BadGreetingsTip2(fruits = listState, isItTriggered = trigger)

}

/**
 * 👍 : the key is set, and when item in the middle is removed, only LazyColumn gets recomposed
 * and not its children
 */
@Composable
fun GoodGreetingsTip2(fruits: List<FruitRow>, isItTriggered: Boolean) {
    LazyColumn(
        modifier = Modifier
            .recomposeHighlighter()
            .recompositionCounter("lazyColumn")
    ) {
        items(
            items = fruits,
            key = { it.name }
        ) { fruit ->
            Item(fruit)
        }
    }
}

/**
 * 👎 : the key is not set, all the items below the removed item were
 * recomposed alongside LazyColumn
 */
@Composable
fun BadGreetingsTip2(fruits: List<FruitRow>, isItTriggered: Boolean) {
    LazyColumn(
        modifier = Modifier
            .recomposeHighlighter()
            .recompositionCounter("lazyColumn")
    ) {
        items(
            items = fruits
        ) { fruit ->
            Item(fruit)
        }
    }
}


@Composable
fun Item(fruit: FruitRow) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = fruit.color)
            .recomposeHighlighter()
    ) {
        Text(
            text = fruit.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
                .recompositionCounter(fruit.name),
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

data class FruitRow(val name: String, val color: Color)