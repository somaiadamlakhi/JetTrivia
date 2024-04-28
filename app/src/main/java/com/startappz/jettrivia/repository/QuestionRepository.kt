package com.startappz.jettrivia.repository

import com.startappz.jettrivia.data.DataOrException
import com.startappz.jettrivia.model.Question
import com.startappz.jettrivia.model.QuestionItem
import com.startappz.jettrivia.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val questionApi: QuestionApi) {

    private val listOfQuestion = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()
    private val dataOrException =
        DataOrException<ArrayList<QuestionItem>,
                Boolean,
                Exception>()


    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            dataOrException.isLoading = true
            dataOrException.data = questionApi.getAllQuestions()
            if (dataOrException.data.toString().isNotEmpty()) dataOrException.isLoading = false
        } catch (ex: Exception) {
            dataOrException.e = ex
            dataOrException.isLoading = false
            ex.printStackTrace()

        }

        return dataOrException
    }
}