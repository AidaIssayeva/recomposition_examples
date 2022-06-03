package com.cupsofcode.recomposition_examples

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cupsofcode.recomposition_examples.ui.theme.*
import kotlinx.coroutines.delay

/**
 * tip 5: utilize `derivedStateOf()` to buffer the rate of changes thus limiting the
 * number of recompositions.
 *
 * If any object depends on a certain state object, that is frequently changed, all composable
 * functions listening to that object, will recompose more than they need to.
 *
 * Wrap that object in `derivedStateOf()` to limit the number of recompositions.
 *
 */
@Composable
fun Tip5() {
    val initialList = arrayListOf<FruitRow>(
        FruitRow("Apple", Gray200),
        FruitRow("Orange", Brown200),
        FruitRow("Strawberry", Green700),
        FruitRow("Blueberry", Brown500),
        FruitRow("Banana", Green200),
        FruitRow("Watermelon", Gray200),
        FruitRow("Cantaloupe", Brown200),
        FruitRow("Mango", Green700),
        FruitRow("Apricot", Brown500),
        FruitRow("Lemon", Green200),
        FruitRow("Grapes", Gray200),
        FruitRow("Peach", Brown200),
        FruitRow("Pear", Green700),
        FruitRow("Kiwi", Brown500),
        FruitRow("Honeydew", Green200),
        FruitRow("Pineapple", Gray200),
        FruitRow("Coconut", Brown200),
        FruitRow("Plum", Green700),
    )


    Column(
        modifier = Modifier
            .recomposeHighlighter()
            .recompositionCounter("column"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GoodGreetingsTip5(initialList)
        // BadGreetingsTip5 (initialList)
    }
}

/**
 * 👍 : Only BottomBar gets recomposed whenever hideBottomBar's calculated state changes,
 * but top-level Scaffold and LazyColumns don't.
 */
@Composable
private fun GoodGreetingsTip5(list: List<FruitRow>) {

    val listState = rememberLazyListState()

    LaunchedEffect(key1 = list, block = {
        delay(500)
        listState.animateScrollToItem(list.lastIndex)
        delay(500)
        listState.animateScrollToItem(0)
    })

    val hideBottomBar = remember {
        derivedStateOf { listState.isScrollInProgress }
    }

    Scaffold(
        modifier = Modifier
            .recomposeHighlighter()
            .recompositionCounter("scaffold"),
        bottomBar = {
            AnimatedVisibility(
                visible = !hideBottomBar.value,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight }
                ),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight }
                )
            ) {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Background200)
                        .recomposeHighlighter()
                        .recompositionCounter("bottombar"),
                    elevation = 32.dp,
                    content = {
                        BottomNavigationItems()
                    }
                )
            }
        },
        content = {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .recomposeHighlighter()
                    .recompositionCounter("lazy column")
            ) {
                items(
                    items = list,
                    key = { it.name }
                ) { fruit ->
                    Item(fruit)
                }
            }
        }
    )
}

/**
 * 👎 : everything including top-level Scaffold gets recomposed, because hideBottomBar
 * depends on the calculated result of the frequent changing isScrollInProgress
 * variable in listState.
 */
@Composable
private fun BadGreetingsTip5(list: List<FruitRow>) {
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = list, block = {
        delay(500)
        listState.animateScrollToItem(list.lastIndex)
        delay(500)
        listState.animateScrollToItem(0)
    })

    val hideBottomBar = listState.isScrollInProgress

    Scaffold(
        modifier = Modifier
            .recomposeHighlighter()
            .recompositionCounter("scaffold"),
        bottomBar = {
            AnimatedVisibility(
                visible = !hideBottomBar,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight }
                ),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight }
                )
            ) {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Background200)
                        .recomposeHighlighter()
                        .recompositionCounter("bottombar"),
                    elevation = 32.dp,
                    content = {
                        BottomNavigationItems()
                    }
                )
            }
        },
        content = {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .recomposeHighlighter()
                    .recompositionCounter("lazy column")
            ) {
                items(
                    items = list,
                    key = { it.name }
                ) { fruit ->
                    Item(fruit)
                }
            }
        }
    )
}

@Composable
private fun BottomNavigationItems() {
    BottomNavigation {
        BottomNavigationItem(
            modifier = Modifier
                .recomposeHighlighter()
                .recompositionCounter("bottomNav item 1")
                .background(color = Background200),
            selected = true, onClick = { }, icon = {
                Icon(Icons.Outlined.Home, "")
            },
            label = {
                Text(text = "Home")
            }
        )

        BottomNavigationItem(
            modifier = Modifier
                .recomposeHighlighter()
                .recompositionCounter("bottomNav item 1")
                .background(color = Background200),
            selected = false, onClick = { }, icon = {
                Icon(Icons.Outlined.AccountCircle, "")
            },
            label = {
                Text(text = "Circle")
            }
        )

        BottomNavigationItem(
            modifier = Modifier
                .recomposeHighlighter()
                .recompositionCounter("bottomNav item 1")
                .background(color = Background200),
            selected = false, onClick = { }, icon = {
                Icon(Icons.Outlined.Favorite, "")
            },
            label = {
                Text(text = "Favorite")
            }
        )
    }
}

