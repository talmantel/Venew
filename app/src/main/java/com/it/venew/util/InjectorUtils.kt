package com.it.venew.util

import android.content.Context
import com.it.venew.data.Repository
import com.it.venew.data.RepositoryImpl
import com.it.venew.data.remote.RetrofitService
import com.it.venew.viewmodel.MainViewModelFactory

object InjectorUtils {

    private fun getRepository(context: Context): Repository {
        return RepositoryImpl.getInstance(RetrofitService.messageApi)
    }

    fun provideMainViewModelFactory(context: Context): MainViewModelFactory {
        val repository = getRepository(context)
        return MainViewModelFactory(repository)
    }
}