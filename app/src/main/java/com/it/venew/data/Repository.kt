package com.it.venew.data

import androidx.lifecycle.LiveData
import com.it.venew.data.model.Supplier
import com.it.venew.data.model.UserLocation

interface Repository {
    fun getSuppliers(): LiveData<List<Supplier>>
    fun refreshSuppliers()

    fun getUserLocations(): LiveData<List<UserLocation>>
    fun refreshUserLocations()
}