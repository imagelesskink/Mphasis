package com.example.mphasis.networking

import com.example.mphasis.SAT_PATH
import com.example.mphasis.SCHOOL_PATH
import com.example.mphasis.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(SCHOOL_PATH)
    suspend fun getAllSchools() : Response<List<School>>

    @GET(SAT_PATH)
    suspend fun getSATScores(
        @Query("dbn") schoolDbn: String
    ) : Response<List<Sat>>
}