package com.example.figmatest

import android.app.Application
import com.example.figmatest.domein.GetListOfItemModelUseCase
import com.example.figmatest.domein.Repository
import com.example.figmatest.ui.ItemViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModul)
        }
    }
        val appModul = module {

        single<Repository> {
            Repository()
        }
        single <GetListOfItemModelUseCase> {
            GetListOfItemModelUseCase(repository = get())
        }
        viewModel<ItemViewModel> {
            ItemViewModel(
                getListOfItemModelUseCase = get()
            )
        }
    }

}