package com.ergi.digimon.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DigimonApiService {

    @GET("digimon")
    suspend fun getDigimonList(
        @Query("pageSize") pageSize: Int = 60
    ): DigimonListResponse

    @GET("digimon/{id}")
    suspend fun getDigimonDetail(
        @Path("id") id: Int
    ): DigimonDetailDto
}
