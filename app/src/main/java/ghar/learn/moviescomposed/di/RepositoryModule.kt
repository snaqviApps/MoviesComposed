package ghar.learn.moviescomposed.di

import ghar.learn.moviescomposed.movielist.data.repository.MoviesListRepositoryImpl
import ghar.learn.moviescomposed.movielist.domain.repository.IMoviesListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     *  val url = URL(BASE_URL) (import java.net.URL)
     *  Network call (blocks IO, needs Coroutines' to be wrapped with 'withContext(Dispatchers)')
     *  while not using 'Retrofit' library
     *
     */

    @Binds
    @Singleton
    abstract fun bindMovieListRepository (
        moviesListRepositoryImpl: MoviesListRepositoryImpl
    ) : IMoviesListRepository
}