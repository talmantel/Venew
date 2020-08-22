package com.it.venew.ui.main

import androidx.lifecycle.*
import com.it.venew.data.Repository
import com.it.venew.data.model.Supplier
import com.it.venew.data.model.UserLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val repository: Repository) : ViewModel(), CoroutineScope {
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val suppliers: LiveData<List<Supplier>>
            = repository.getSuppliers()

    val userLocations: LiveData<List<UserLocation>>
            = repository.getUserLocations()


    fun refreshSuppliers(){
        repository.refreshSuppliers()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
