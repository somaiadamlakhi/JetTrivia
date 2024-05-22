package com.startappz.jettrivia.screens

import androidx.compose.runtime.Composable
import com.startappz.jettrivia.components.Questions

@Composable
fun TriviaHome(viewModel: QuestionViewModel) {
    Questions(viewModel)
}