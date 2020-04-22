package edu.uoc.android.rest

import edu.uoc.android.ApiConstants
import edu.uoc.android.rest.models.Museums
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    companion object {
        fun getMuseums(): Call<Museums> {
            val client = OkHttpClient()
            val retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val museumService = retrofit.create(MuseumService::class.java)
            return museumService.museums("0", "20")
        }
    }
}


