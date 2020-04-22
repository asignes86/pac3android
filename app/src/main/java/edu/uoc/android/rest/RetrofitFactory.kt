package edu.uoc.android.rest

import edu.uoc.android.ApiConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    val museumAPI: MuseumService
        get() {
            val retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.API_URL)
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(MuseumService::class.java)
        }
}

