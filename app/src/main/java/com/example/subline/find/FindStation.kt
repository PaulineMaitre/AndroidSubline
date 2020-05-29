package com.example.subline.find

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.TYPE_METRO
import com.example.subline.utils.retrofit
import kotlinx.android.synthetic.main.fragment_find_metros.*
import kotlinx.android.synthetic.main.fragment_find_station.*
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass.
 */
class FindStation: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_find_station, container, false)


        val searchStation = view.findViewById<AutoCompleteTextView>(R.id.searchStation)

        var listStations = arrayListOf<String>("Chatelet", "Denfert-Rochereau")

        val adapter = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, listStations)
        searchStation.threshold = 0
        searchStation.setAdapter(adapter)
        searchStation.setOnItemClickListener { parent, viewSearch, position, id ->
            hideKeyboard(view.context, view)
            testTV.text = parent.getItemAtPosition(position).toString()
        }

        return view
    }


    private fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}

