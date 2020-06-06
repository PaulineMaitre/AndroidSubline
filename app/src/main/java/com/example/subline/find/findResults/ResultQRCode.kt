package com.example.subline.find.findResults

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.data.AllLines
import com.example.subline.data.DatabaseJSON
import com.example.subline.utils.*
import java.io.File

class ResultQRCode : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_qr_code)

        val stationSlug = intent.getStringExtra("stationSlug")

        val f = File(this?.filesDir,"TransportLines.json")
        if (f.length() == 0L) {
            DatabaseJSON().save(this,"TransportLines.json", requestAllLines())
        }
        val lines = DatabaseJSON().load(this,"TransportLines.json", AllLines.Lines::class.java)

        displayResultsQRCode(lines, stationSlug)

    }

    private fun displayResultsQRCode(lines: AllLines.Lines, stationSlug: String) {
        val listLinesTV = findViewById<TextView>(R.id.listLinesResultQRCodeTextView)
        val linesByStation: ArrayList<AllLines.Line> = getLinesByStation(this, listLinesTV, lines, stationSlug)
        val listPicto = getListPicto(this, linesByStation)
        val stationName = getNameFromSlug(stationSlug, lines)
        listLinesTV.text = resources.getText(R.string.listLinesToComplete).toString() + " " + stationName + " :"
        val allLinesInStationRv = findViewById<RecyclerView>(R.id.allLinesInStationQRCodeRv)
        setAdapterQRCodeResult(this, allLinesInStationRv, stationName, linesByStation, listPicto)
    }
}
