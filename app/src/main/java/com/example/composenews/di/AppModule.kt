package com.example.composenews.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.example.composenews.data.local.NewsDao
import com.example.composenews.data.local.NewsDatabase
import com.example.composenews.data.local.remotemediator.NewsKeyDao
import com.example.composenews.data.local.remotemediator.PagingNewsDao
import com.example.composenews.data.local.source.SourceDao
import com.example.composenews.data.remote.api.NewsApi
import com.example.composenews.repository.NewsRepository
import com.example.composenews.repository.NewsRepositoryImpl
import com.example.composenews.utils.Constants.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalPagingApi
abstract class AppModule {

    companion object {
        @Provides
        @Singleton
        fun retrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        @Provides
        @Singleton
        fun newsApi(retrofit: Retrofit) = retrofit.create(NewsApi::class.java)

        @Provides
        @Singleton
        fun database(application: Application): NewsDatabase =
            Room.databaseBuilder(application.applicationContext, NewsDatabase::class.java, "news_db")
                .build()

        @Provides
        @Singleton
        fun newsDao(database: NewsDatabase): NewsDao = database.newsDao()

        @Provides
        @Singleton
        fun sourceDao(database: NewsDatabase): SourceDao = database.sourceDao()

        @Provides
        @Singleton
        fun keysDap(database: NewsDatabase): NewsKeyDao = database.newsKeyDao()

        @Provides
        @Singleton
        fun pagingNewsDao(database: NewsDatabase): PagingNewsDao = database.pagingNewsDao()

    }

    @Binds
    abstract fun repository(repositoryImpl: NewsRepositoryImpl): NewsRepository
}