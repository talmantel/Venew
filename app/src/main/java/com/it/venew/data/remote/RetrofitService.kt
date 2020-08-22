package com.it.venew.data.remote

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val URL = "http://booking.veg.tours/venew/blocks/"
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttp = OkHttpClient.Builder().addInterceptor(logger)

    val messageApi: VenewApi = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()))
        .client(okHttp.build())
        .build()
        .create(VenewApi::class.java)
}