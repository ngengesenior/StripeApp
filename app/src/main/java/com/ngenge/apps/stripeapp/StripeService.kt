package com.ngenge.apps.stripeapp

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface StripeService {


    @POST("charge")
    suspend fun createCharge(@Body charge: Charge):Response<StripeDataResponse>

    companion object {

        //Replace this with your server endpoint
        private const val BASE_ENDPOINT = "YOUR_SERVER"

        private fun create(httpUrl: String): StripeService {
            val okHttpClient = OkHttpClient.Builder()

            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build()
                .create(StripeService::class.java)
        }

        fun create() = create(BASE_ENDPOINT)


    }
}