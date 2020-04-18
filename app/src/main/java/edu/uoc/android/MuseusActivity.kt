package edu.uoc.android

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import edu.uoc.android.rest.MuseumService
import edu.uoc.android.rest.models.Museums
import kotlinx.android.synthetic.main.activity_museus.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MuseusActivity : AppCompatActivity() {
    val TAG = "MuseusActivity"
    var museums: Museums = Museums()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museus)

        //Create RV
        rv_museums.layoutManager = LinearLayoutManager(this)

        //load Museums
        getMuseums()
    }

    fun getMuseums() {
        val client = OkHttpClient()
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val museumService = retrofit.create(MuseumService::class.java)

        val call = museumService.museums("0", "20")
        call.enqueue(object : Callback<Museums> {
            override fun onResponse(call: Call<Museums>, response: Response<Museums>) {
                if (response.code() == 200) {
                    // showProgress( false );
                    museums = response.body() as Museums
                    Log.i(TAG, "Size: ${museums.elements.size}")
                    rv_museums.adapter = MyMuseumItemRecyclerViewAdapter(museums.elements)

                    Log.d(TAG, museums.toString())
                }
            }

            override fun onFailure(call: Call<Museums>, t: Throwable) {
                Log.d(TAG, t.message!!)
            }
        })
    }
}
