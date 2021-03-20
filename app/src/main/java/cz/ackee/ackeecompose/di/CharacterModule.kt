package cz.ackee.ackeecompose.di

import android.content.Context
import androidx.paging.PagingSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.ackee.ackeecompose.data.room.DatabaseProvider
import cz.ackee.ackeecompose.data.CharacterPagedDataSource
import cz.ackee.ackeecompose.data.api.CharacterService
import cz.ackee.ackeecompose.data.sharedprefs.CharacterSharedPrefs
import cz.ackee.ackeecompose.domain.Character
import cz.ackee.ackeecompose.domain.CharacterRepository
import cz.ackee.ackeecompose.domain.CharacterRepositoryImpl
import cz.ackee.ackeecompose.ui.list.CharacterListViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

val characterModule = module {

    viewModel { CharacterListViewModel(get()) }

    single<CharacterRepository> { CharacterRepositoryImpl(get(), get()) }

    single<PagingSource<Int, Character>> { CharacterPagedDataSource(get(), get(), get()) }

    remote()
    sharedPrefs()
    database()
}

private fun Module.remote() {

    single<CharacterService> {
        get<Retrofit>().create()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
    }

    single {
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    single {
        OkHttpClient.Builder().build()
    }
}

private fun Module.sharedPrefs() {

    single {
        val prefs = androidContext().getSharedPreferences("characters", Context.MODE_PRIVATE)
        CharacterSharedPrefs(prefs)
    }
}

private fun Module.database() {

    single {
        DatabaseProvider(androidContext())
    }

    single {
        get<DatabaseProvider>().provideAppDatabase().characterDao()
    }
}