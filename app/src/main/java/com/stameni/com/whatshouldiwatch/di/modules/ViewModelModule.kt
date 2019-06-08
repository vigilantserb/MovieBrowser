package com.stameni.com.whatshouldiwatch.di.modules

import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
import com.stameni.com.whatshouldiwatch.data.networkdata.FetchGenreListUseCase
import com.stameni.com.whatshouldiwatch.screens.discover.genre.GenreMoviesViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import java.lang.annotation.RetentionPolicy
import javax.inject.Provider
import kotlin.reflect.KClass
import kotlin.annotation.Target
import kotlin.annotation.Retention

@Module
class ViewModelModule {

//    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
//    @Retention(AnnotationRetention.RUNTIME)
//    @MapKey
//    internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Provides
    internal fun viewModelFactory(genreMoviesViewModel: GenreMoviesViewModel): ViewModelFactory {
        return ViewModelFactory(genreMoviesViewModel)
    }

    @Provides
    internal fun genreMoviesViewModel(fetchGenreListUseCase: FetchGenreListUseCase): GenreMoviesViewModel {
        return GenreMoviesViewModel(fetchGenreListUseCase)
    }
}
