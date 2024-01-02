package ghar.learn.moviescomposed.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ghar.learn.moviescomposed.BuildConfig.BASE_URL
import ghar.learn.moviescomposed.movielist.data.local.movies.MovieDatabase
import ghar.learn.moviescomposed.movielist.data.remote.MovieApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor = interceptor)
        .build()

    @Provides
    @Singleton
    fun providesMovieApi() : MovieApi = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(client)
        .build()
        .create(MovieApi::class.java)

    @Provides
    @Singleton
    fun providesMovieDatabase(app: Application) : MovieDatabase {
        return Room
            .databaseBuilder(app, MovieDatabase::class.java, "movieDB.db")
            .build()
    }

}