package com.it.venew.data.remote

import com.it.venew.data.model.Supplier
import com.it.venew.data.model.UserLocation
import retrofit2.Call
import retrofit2.http.GET

interface VenewApi {

    @GET("get_suppliers.php")
    fun getSuppliers(): Call<List<Supplier>>

    @GET("get_user_locations.php")
    fun getUserLocations(): Call<List<UserLocation>>
}