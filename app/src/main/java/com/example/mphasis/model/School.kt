package com.example.mphasis.model

import com.google.gson.annotations.SerializedName

data class School(
    @SerializedName("dbn")
    val dbn: String,
    @SerializedName("overview_paragraph")
    val overview_paragraph: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("fax_number")
    val faxNumber: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("neighborhood")
    val neighborhood: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("primary_address_line_1")
    val primaryAddressLine1: String,
    @SerializedName("school_email")
    val schoolEmail: String,
    @SerializedName("school_name")
    val schoolName: String,
    @SerializedName("state_code")
    val stateCode: String,
    @SerializedName("total_students")
    val transfer: String,
    @SerializedName("website")
    val website: String,
    @SerializedName("zip")
    val zip: String
)