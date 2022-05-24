package com.cupsofcode.recomposition_examples

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cupsofcode.recomposition_examples.ui.theme.Brown200
import com.cupsofcode.recomposition_examples.ui.theme.Gray200

/**
 * tip #1. Break down composable functions as much as possible.
 *
 * Nested composable functions notify the nearest parent in the node tree until it reaches
 * the nearest recomposition scope, thus causing it to recompose all children inside.
 */
@Composable
fun Tip1() {
    GoodGreetings()
    // BadGreetings()
}

/**
 * 👍 : only associated column with changes gets recomposed.
 */
@Composable
fun GoodGreetings() {
    Column(modifier = Modifier.recompositionCounter("outerColumn")) {
        Greeting("Android", 1, Gray200)
        Greeting("Compose", 2, Brown200)
    }
}

@Composable
fun Greeting(name: String, columnNumber: Int, background: Color) {
    val clicks = remember {
        mutableStateOf(0)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = background)
            .clickable {
                clicks.value++
            }
            .recompositionCounter(getCallingName("column", columnNumber))
    ) {
        Text(
            text = "Hello $name! ${clicks.value}", modifier = Modifier
                .padding(all = 16.dp)
                .recompositionCounter(getCallingName("text1", columnNumber))
        )
        Button(
            onClick = { },
            modifier = Modifier
                .padding(all = 16.dp)
                .recompositionCounter(getCallingName("button", columnNumber))
        ) {
            Text(
                text = "Button", modifier = Modifier
                    .recompositionCounter(getCallingName("text2", columnNumber))
            )
        }

    }
}

/**
 * 👎 : both inner columns alongside with outer column get recomposed
 */
@Composable
fun BadGreetings() {
    val clicks1 = remember {
        mutableStateOf(0)
    }
    val clicks2 = remember {
        mutableStateOf(0)
    }

    Column(modifier = Modifier.recompositionCounter("outerColumn")) {
        // Column 1
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Gray200)
                .clickable {
                    clicks1.value++
                }
                .recompositionCounter(getCallingName("column", 1))
        ) {
            Text(
                text = "Hello Android ${clicks1.value}", modifier = Modifier
                    .padding(all = 16.dp)
                    .recompositionCounter(getCallingName("text1", 1))
            )
            Button(
                onClick = { },
                modifier = Modifier
                    .padding(all = 16.dp)
                    .recompositionCounter(getCallingName("button", 1))
            ) {
                Text(
                    text = "Button", modifier = Modifier
                        .recompositionCounter(getCallingName("text2", 1))
                )
            }
        }
        // Column 2
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Brown200)
                .clickable {
                    clicks2.value++
                }
                .recompositionCounter(getCallingName("column", 2))
        ) {
            Text(
                text = "Hello Compose ${clicks2.value}!", modifier = Modifier
                    .padding(all = 16.dp)
                    .recompositionCounter(getCallingName("text1", 2))
            )
            Button(
                onClick = { },
                modifier = Modifier
                    .padding(all = 16.dp)
                    .recompositionCounter(getCallingName("button", 2))
            ) {
                Text(
                    text = "Button", modifier = Modifier
                        .recompositionCounter(getCallingName("text2", 2))
                )
            }

        }

    }
}

private fun getCallingName(name: String, columnNumber: Int): String {
    return "$columnNumber column: $name"
}