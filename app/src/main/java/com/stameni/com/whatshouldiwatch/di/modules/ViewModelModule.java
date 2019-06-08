package com.stameni.com.whatshouldiwatch.di.modules;

import androidx.lifecycle.ViewModel;
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory;
import com.stameni.com.whatshouldiwatch.data.networkData.FetchGenreListUseCase;
import com.stameni.com.whatshouldiwatch.screens.discover.genre.GenreMoviesViewModel;
import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

import javax.inject.Provider;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

@Module
public class ViewModelModule {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    @Provides
    ViewModelFactory viewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }

    @Provides
    @IntoMap
    @ViewModelKey(GenreMoviesViewModel.class)
    ViewModel questionDetailsViewModel(FetchGenreListUseCase fetchGenreListUseCase) {
        return new GenreMoviesViewModel(fetchGenreListUseCase);
    }
}
