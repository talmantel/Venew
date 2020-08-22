package com.it.venew.data.model

data class Supplier (
    val id: Int,
    val locationId: Int,
    val name: String,
    val note: String?,
    val lat: Double?,
    val lon: Double?,
    val internetService: Boolean,
    val email: String?,
    val website: String?,
    val address: String?,
    val phoneNumber: String?,
    val ts: String?
)