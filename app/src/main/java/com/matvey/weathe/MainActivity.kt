package com.matvey.weathe

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.matvey.weathe.R.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.*


class MainActivity : MvpAppCompatActivity(), MainView {

    private val mainPresentor by moxyPresenter {MainPresentor()}

    ///--------------------------------------view objects------------------------------------
    lateinit var buttonsheet:Button;
    lateinit var info_about_day:Button
    lateinit var var_meaning_weather:TextView
    lateinit var var_meaning_carruent_temp:TextView
    lateinit var var_meaning_day_temp:TextView
    lateinit var var_meaning_night_temp:TextView
    lateinit var var_meaning_humidity:TextView
    lateinit var var_meaning_pressure:TextView
    lateinit var var_meaning_wind:TextView
    lateinit var var_meaning_sunrise:TextView
    lateinit var var_meaning_sunset:TextView
    lateinit var var_meaning_daytime:TextView
    //---------------------------------------------------------------------------------------

    private val LOCATION_PERMISSION_REQ_CODE = 1000;
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private val dateFormat = SimpleDateFormat("EEE, d  MMM   yyyy   |   HH:mm")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(layout.activity_main)
        searchelements();
        initializationmeaning();
        mainPresentor.enable();


        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.refreshLayout)

        // Refresh function for the layout
        swipeRefreshLayout.setOnRefreshListener{
            swipeRefreshLayout.isRefreshing = false
            getCurrentLocation()
            info_about_day.text = dateFormat.format(Date()).toString()

        }

        buttonsheet.setOnClickListener{
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.dialog_layout,null)
            dialog.setContentView(view)
            dialog.show()
            getCurrentLocation()
            info_about_day.text = dateFormat.format(Date()).toString()

        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


    }
    //------------------------- search_elements------------------------------------------
    private fun searchelements()
    {
        buttonsheet = findViewById(R.id.buttonsheets)
        info_about_day = findViewById(R.id.infodaybutton)
        var_meaning_weather = findViewById(R.id.meaning_weather)
        var_meaning_carruent_temp = findViewById(R.id.meaning_carruent_temp)
        var_meaning_day_temp = findViewById(R.id.meaning_day_temp)
        var_meaning_night_temp = findViewById(R.id.meaning_night_temp)
        var_meaning_humidity = findViewById(R.id.meaning_humidity)
        var_meaning_pressure = findViewById(R.id.meaning_pressure)
        var_meaning_wind = findViewById(R.id.meaning_wind)
        var_meaning_sunrise = findViewById(R.id.meaning_sunrise)
        var_meaning_sunset = findViewById(R.id.meaning_sunset)
        var_meaning_daytime = findViewById(R.id.meaning_daytime)
    }
    //------------------------- search_elements------------------------------------------
    ///------------------------- initialization------------------------------------------
    private fun initializationmeaning()
    {
        buttonsheet.text = "0"
        info_about_day.text = "0"
        var_meaning_weather.text = "0"
        var_meaning_carruent_temp.text = "0"
        var_meaning_day_temp.text = "0"
        var_meaning_night_temp.text = "0"
        var_meaning_humidity.text = "0"
        var_meaning_pressure.text = "0"
        var_meaning_wind.text = "0"
        var_meaning_sunrise.text = "0"
        var_meaning_sunset.text = "0"
        var_meaning_daytime.text = "0"
    }
    ///----------------------------------------------------------------------------------

    /// ------------------------- location code------------------------------------------
    private fun getCurrentLocation() {
        val gcd = Geocoder(this, Locale.getDefault())
        val addresses = gcd.getFromLocation(latitude, longitude, 1)

        // checking location permission
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // request permission
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE);
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                // getting the last known or current location
                latitude = location.latitude
                longitude = location.longitude
                if (addresses.size > 0)
                {
                    buttonsheet.text = addresses[0].locality.toString()
                }

            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed on getting current location",
                    Toast.LENGTH_SHORT).show()
            }
        mainPresentor.refresh(latitude.toString(),longitude.toString())
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                } else {
                    // permission denied
                    Toast.makeText(this, "You need to grant permission to access location",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //--------------------------------------Moxy code --------------------------------------------------------


    override fun displayCurrentTemp(data: WeatherData) {
        buttonsheet.text = "0"
        info_about_day.text = "0"
        var_meaning_weather.text = "0"
        var_meaning_carruent_temp.text = "0"
        var_meaning_day_temp.text = "0"
        var_meaning_night_temp.text = "0"
        var_meaning_humidity.text = "0"
        var_meaning_pressure.text = "0"
        var_meaning_wind.text = "0"
        var_meaning_sunrise.text = "0"
        var_meaning_sunset.text = "0"
        var_meaning_daytime.text = "0"
    }

    override fun displayHourlyData(data: List<HourlyWeatherModel>) {

        data.map { it.updatedata() }//реализовать обновление нормально
    }

    override fun displayDaillyData(data: List<DaillyWeatherModel>) {
        data.map { it }//реализовать обновление нормально
    }

    override fun displayError(error: Throwable) {

    }

    override fun setLoading(flags: Boolean) {

    }

    //----------------------------------------------------------------------------------------------------


}