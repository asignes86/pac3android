package edu.uoc.android

import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import edu.uoc.android.rest.MuseumService
import edu.uoc.android.rest.models.Museums
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


open class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    val TAG = MapsActivity::class.simpleName

    //Constants
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 10
    private val DEFAULT_ZOOM: Float = 17.0F

    //locations
    private lateinit var mMap: GoogleMap
    private lateinit var mLastKnownLocation: Location
    private var mLocationPermissionsGaranted: Boolean = false

    private lateinit var museums: Museums

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment


        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        setMuseumsLocations()
        getLocationPermission()
        updateLocationUI()
        getDeviceLocation()
    }

    fun getDeviceLocation() {
        val mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        try {
            if (mLocationPermissionsGaranted) {
                mFusedLocationProviderClient?.lastLocation?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = task.result!!
                        mMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    mLastKnownLocation.latitude,
                                    mLastKnownLocation.longitude
                                ), DEFAULT_ZOOM
                            )
                        )

                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        mMap.uiSettings.isMyLocationButtonEnabled = false
                    }
                }


            }
        } catch (e: SecurityException) {
            Log.e(TAG, e.message!!)
        }
    }

    protected fun updateLocationUI() {
        try {
            if (mLocationPermissionsGaranted) {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                mMap.isMyLocationEnabled = true
                mMap.uiSettings.isMyLocationButtonEnabled = true
            } else {
                mMap.isMyLocationEnabled = false
                mMap.uiSettings.isMyLocationButtonEnabled = false
            }
        } catch (e: SecurityException) {
            Log.e(TAG, e.message!!)
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationPermissionsGaranted = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mLocationPermissionsGaranted = false
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGaranted = true
            }
        }
        updateLocationUI()
    }

    fun setMuseumsLocations() {
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
                    val markerBitmap =
                        BitmapFactory.decodeResource(resources, R.drawable.ic_marker_museum)
                    for (element in museums.elements) {
                        val coordString = element.localitzacio.split(",")
                        val coord = LatLng(coordString[0].toDouble(), coordString[1].toDouble())
                        mMap.addMarker(
                            MarkerOptions()
                                .position(coord)
                                .title(element.adrecaNom)
                                .icon(BitmapDescriptorFactory.fromBitmap(markerBitmap))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<Museums>, t: Throwable) {
                Log.d(TAG, t.message!!)
            }
        })
    }
}

