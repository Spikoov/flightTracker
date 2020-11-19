package com.example.flighttracker

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    val DATE_FORMAT = "dd MMM yyy"

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val airportNamesList = ArrayList<String>()
        for(airport in viewModel.getAirportListLiveData().value!!){
            airportNamesList.add(airport.getFormattedName())
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            airportNamesList
        )
        adapter.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item))

        spinner_airport.adapter = adapter

        viewModel.getBeginDateLiveData()
            .observe(this, androidx.lifecycle.Observer{ displaySelectedDate(fromDate, it) })
        viewModel.getEndDateLiveData()
            .observe(this, androidx.lifecycle.Observer{ displaySelectedDate(toDate, it) })

        fromDate.setOnClickListener { showDatePicker(fromDate) }
        toDate.setOnClickListener { showDatePicker(toDate) }

        searchButton.setOnClickListener { search() }
    }

    private fun showDatePicker(clickedView: View){
        val calendar = if(clickedView.id == fromDate.id){
            viewModel.getBeginDateLiveData().value!!
        }
        else{
            viewModel.getEndDateLiveData().value!!
        }
        val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener{view, year, monthOfYear, dayOfMonth ->
                    if (clickedView.id == fromDate.id){
                        viewModel.updateBeginCalendar(year, monthOfYear, dayOfMonth)
                    }
                    else{
                        viewModel.updateEndCalendar(year, monthOfYear, dayOfMonth)
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun displaySelectedDate(textView: TextView, calendar: Calendar){
        textView.text = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(calendar.time)
    }

    private fun search(){
        // get selected airport
        val icao = viewModel.getAirportListLiveData().value!!.get(spinner_airport.selectedItemPosition).icao

        // get isArrival
        val isArrival= switch1.isChecked

        //get fromDate and toDate
        val begin = viewModel.getBeginDateLiveData().value!!.timeInMillis / 1000
        val end = viewModel.getEndDateLiveData().value!!.timeInMillis / 1000


        // start activity and send data

    }
}