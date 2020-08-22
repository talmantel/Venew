package com.it.venew.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.it.venew.data.model.Supplier
import com.it.venew.data.model.UserLocation
import com.it.venew.data.remote.VenewApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl
    private constructor(private val venewApi: VenewApi): Repository{

    companion object {
        @Volatile private var instance: RepositoryImpl? = null

        fun getInstance(venewApi: VenewApi) =
            instance ?: synchronized(this) {
                instance ?: RepositoryImpl(venewApi).also { instance = it }
            }
    }

    private val suppliers = MutableLiveData<List<Supplier>>()
    private val userLocations = MutableLiveData<List<UserLocation>>()

    override fun getSuppliers(): LiveData<List<Supplier>>{
        refreshSuppliers();
        return suppliers
    }

    override fun refreshSuppliers() {
        venewApi.getSuppliers().enqueue(object : Callback<List<Supplier>>{
            override fun onResponse(vall: Call<List<Supplier>>, response: Response<List<Supplier>>) {
                suppliers.value = response.body()
            }

            override fun onFailure(call: Call<List<Supplier>>, t: Throwable) {}

        })
    }

    override fun getUserLocations(): LiveData<List<UserLocation>>{
        refreshUserLocations();
        return userLocations
    }

    override fun refreshUserLocations() {
        venewApi.getUserLocations().enqueue(object : Callback<List<UserLocation>>{
            override fun onResponse(vall: Call<List<UserLocation>>, response: Response<List<UserLocation>>) {
                userLocations.value = response.body()
            }

            override fun onFailure(call: Call<List<UserLocation>>, t: Throwable) {}

        })
    }
}