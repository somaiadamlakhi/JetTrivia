package com.startappz.jettrivia.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.startappz.jettrivia.model.QuestionItem
import com.startappz.jettrivia.screens.QuestionViewModel
import com.startappz.jettrivia.util.AppColors

@Composable
fun Questions(viewModel: QuestionViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()
    val isLoading = viewModel.data.value.isLoading
    val questionIndex = remember {
        mutableStateOf(0)
    }
    if (isLoading == true) {
        CircularProgressIndicator()
    } else {
//        questions?.forEach { question ->
        val question = try {
            questions?.get(questionIndex.value)
        } catch (ex: Exception) {
            null
        }
        QuestionTracker()
        question?.let {
            QuestionDisplay(
                question = it,
                questionIndex = questionIndex
            )
        }
//        }

    }
//    println(questions?.size)
}

@Composable
fun QuestionDisplay(
    question: QuestionItem,
    questionIndex: MutableState<Int>,
//    viewModel: QuestionViewModel,
    onNextClicked: (Int) -> Unit = {}
) {
    val choiceState = remember(question) {
        question.choices.toMutableList()
    }
    val answerState = remember(question) {
        mutableStateOf<Int?>(null)
    }

    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)
    }
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    val updateAnswer: (Int) -> Unit = remember(question) {
        {
            answerState.value = it
//            correctAnswerState.value = (choiceState[it] == question.answer)
            correctAnswerState.value = (it == question.choices.indexOf(question.answer))

        }
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = AppColors.mDarkPurple
    ) {
        Column(
            modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start

        ) {

            DrawDottedLine(pathEffect = pathEffect)
            Text(
                modifier = Modifier
                    .padding(6.dp)
                    .align(Alignment.Start)
                    .fillMaxHeight(0.3f),
                text = question.question,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 22.sp,
                color = AppColors.mOffWhite
            )

            //Choices
            choiceState.forEachIndexed { index, answerText ->
                Row(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .height(45.dp)
                        .border(
                            width = 4.dp, brush = Brush.linearGradient(
                                colors = listOf(AppColors.mOffDarkPurple, AppColors.mOffDarkPurple),
                            ), shape = RoundedCornerShape(15.dp)
                        )
                        .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp, bottomStart = 50.dp, bottomEnd = 50.dp))
                        .background(Color.Transparent),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (answerState.value == index),
                        onClick = {
                            updateAnswer(index)
                        },
                        modifier = Modifier.padding(16.dp),
                        colors = RadioButtonDefaults.colors(
                            selectedColor =
                            if (correctAnswerState.value == true
                                && index == answerState.value
                            ) {
                                Color.Green
                            } else {
                                Color.Red
                            }
                        )
                    )//End Radio Button

                    val annotatedString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Light,
                                color = if (correctAnswerState.value == true
                                    && index == answerState.value
                                ) {
                                    Color.Green
                                } else if (correctAnswerState.value == false
                                    && index == answerState.value
                                ) {
                                    Color.Red
                                } else {
                                    AppColors.mOffWhite
                                },
                                fontSize = 17.sp
                            )
                        ) {
                            append(answerText)
                        }

                    }
                    Text(text = annotatedString, modifier = Modifier.padding(6.dp))

                }

            }

            Button(
                onClick = {
                    if (answerState.value != null) {
                        questionIndex.value = questionIndex.value.plus(1)
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(3.dp),

                shape = RoundedCornerShape(15.dp)
            ) {
                Text(text = "Next")
            }

        }
    }
}


@Composable
fun QuestionTracker(
    counter: Int = 10, outOf: Int = 100
) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
                withStyle(
                    style = SpanStyle(
                        color = AppColors.mLightGray, fontWeight = FontWeight.Bold, fontSize = 27.sp
                    )
                ) {
                    append("Question $counter/")
                    withStyle(
                        style = SpanStyle(
                            color = AppColors.mLightGray, fontWeight = FontWeight.Light, fontSize = 14.sp
                        )
                    ) {
                        append("$outOf")
                    }

                }
            }
        }, modifier = Modifier.padding(20.dp)
    )

}

@Composable
fun DrawDottedLine(pathEffect: PathEffect) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
    ) {

        drawLine(
            color = Color.White, start = Offset(0f, 0f), end = Offset(size.width, 0f), pathEffect = pathEffect
        )
    }
}