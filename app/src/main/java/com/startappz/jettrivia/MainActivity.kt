package com.startappz.jettrivia

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.startappz.jettrivia.screens.QuestionViewModel
import com.startappz.jettrivia.screens.TriviaHome
import com.startappz.jettrivia.ui.theme.JetTriviaTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val vm by viewModels<QuestionViewModel>()

            JetTriviaTheme {
                TriviaHome(vm)

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetTriviaTheme {

    }
}