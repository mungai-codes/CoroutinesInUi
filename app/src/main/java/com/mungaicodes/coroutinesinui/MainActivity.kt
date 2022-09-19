package com.mungaicodes.coroutinesinui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mungaicodes.coroutinesinui.ui.theme.CoroutinesInUiTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoroutinesInUiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainUi()
                }
            }
        }
    }
}

@Composable
fun MainUi(modifier: Modifier = Modifier) {
    val counter: MutableState<Int> = remember {
        mutableStateOf(0)
    }
    val scope: CoroutineScope = rememberCoroutineScope()

    var currentJob by remember {
        mutableStateOf<Job?>(null)
    }

    MaterialTheme {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .height(200.dp)
                .padding(10.dp)
        ) {
            Text(counter.value.toString())
            Button(
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 10.dp),
                onClick = {
                    currentJob = scope.launch { increment(counter) }
                }) {
                Text("click to Increment")
            }
            Button(
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 10.dp),
                onClick = {
                    currentJob?.cancel()
                    currentJob = null
                },
                enabled = currentJob != null
            ) {
                Text("Cancel")
            }

        }
    }
}

suspend fun increment(counter: MutableState<Int>) {

    while (true) {
        delay(1000)
        counter.value += 1
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CoroutinesInUiTheme {
        MainUi()
    }
}