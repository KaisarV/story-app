package com.kai.storyapp.view.map

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.kai.storyapp.R
import com.kai.storyapp.databinding.ActivityStoryMapsBinding
import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.model.response.ListStoryLocationItem
import com.kai.storyapp.view.ViewModelFactory
import com.kai.storyapp.view.home.HomeViewModel
import com.kai.storyapp.view.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class StoryMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityStoryMapsBinding
    private val boundsBuilder = LatLngBounds.Builder()
    private lateinit var storyMapViewModel: StoryMapViewModel
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoryMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setupViewModel()
    }

    private fun setupViewModel() {
        storyMapViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[StoryMapViewModel::class.java]

        storyMapViewModel.getUser().observe(this) { user ->
            token = user.token
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        storyMapViewModel.getStoryLocation(token!!)
        storyMapViewModel.storyMapResponse.observe(this) { storyMap ->
            addManyMarker(storyMap.listStory)
        }

    }

    data class TourismPlace(
        val name: String,
        val latitude: Double,
        val longitude: Double
    )

    private fun addManyMarker(listStoryMap: List<ListStoryLocationItem>) {

        listStoryMap.forEach { listStory ->

            val lat = convertToDouble(listStory.lat)
            val lon = convertToDouble(listStory.lon)

            if (lat != null && lon != null) {
                val latLng = LatLng(lat, lon)
                mMap.addMarker(MarkerOptions().position(latLng).title(listStory.name))
                boundsBuilder.include(latLng)
            }
        }

        val bounds: LatLngBounds = boundsBuilder.build()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                300
            )
        )
    }

    private fun convertToDouble(value: Any): Double? {
        return when (value) {
            is Double -> value
            is String -> value.toDoubleOrNull()
            else -> null
        }
    }

}