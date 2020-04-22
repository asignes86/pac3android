package edu.uoc.android.museus

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import edu.uoc.android.R
import edu.uoc.android.rest.RetrofitFactory
import edu.uoc.android.rest.models.Museums
import kotlinx.android.synthetic.main.activity_museus.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MuseusActivity : AppCompatActivity() {
    val TAG = MuseusActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museus)

        //Create RV
        rv_museums.layoutManager = LinearLayoutManager(this)

        //load Museums
        getMuseums()
    }

    private fun getMuseums() {
        //show progressBar while loading data
        showProgress(true)
        RetrofitFactory.museumAPI.museums("0", "20").enqueue(object : Callback<Museums> {
            override fun onResponse(call: Call<Museums>, response: Response<Museums>) {
                if (response.code() == 200) {
                    showProgress(false)
                    val museums = response.body()!!

                    Log.i(TAG, "Number of Elements returned by Museums: ${museums.elements.size}")

                    //load adapter with museums elements
                    rv_museums.adapter =
                        MyMuseumItemRecyclerViewAdapter(
                            museums.elements
                        )
                } else {
                    Log.e(TAG, "ResponseCode: ${response.code()} msg: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Museums>, t: Throwable) {
                Log.d(TAG, t.message!!)
            }
        })
    }

    fun showProgress(show: Boolean) {
        if (show) {
            pb_museums.visibility = View.VISIBLE
        } else {
            pb_museums.visibility = View.INVISIBLE
        }
    }

}
