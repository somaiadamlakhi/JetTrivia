package com.startappz.jettrivia

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.startappz.jettrivia.ui.theme.JetTriviaTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetTriviaTheme {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    JetTriviaTheme {
//
//    }
}