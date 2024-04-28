package com.startappz.jettrivia.di

import com.startappz.jettrivia.network.QuestionApi
import com.startappz.jettrivia.repository.QuestionRepository
import com.startappz.jettrivia.util.Constants
import com.startappz.jettrivia.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun getQuestionRepository(api: QuestionApi): QuestionRepository = QuestionRepository(api)


    @Singleton
    @Provides
    fun provideQuestionApi(): QuestionApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionApi::class.java)

    }
}