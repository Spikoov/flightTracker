package com.example.flighttracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val airportNamesList = ArrayList<String>()
        val airportList = Utils.generateAirportList()
        for(airport in airportList){
            airportNamesList.add(airport.getFormattedName())
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            airportNamesList
        )
        adapter.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item))

        //spinner_airport.adapter =
    }
}