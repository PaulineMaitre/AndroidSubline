package com.example.subline.find

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.subline.find.findResults.ResultQRCode
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class QRCode : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private val REQUEST_CAMERA = 1
    var  scannerView : ZXingScannerView? = null
    private var txtResult : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerView = ZXingScannerView(this)
        setContentView(scannerView)
        setContentView(scannerView)

        if(!checkPermission())
            requestPermission()
    }

    override fun onResume() {
        super.onResume()
        if(checkPermission()){
            if(scannerView == null){
                scannerView = ZXingScannerView(this)
                setContentView(scannerView)
            }
            scannerView?.setResultHandler(this)
            scannerView?.startCamera()
        }
    }

    override fun handleResult(p0: Result?) {
        var stationSlug = ""
        stationSlug = p0?.text.toString()
        val intent = Intent(this, ResultQRCode::class.java)
        intent.putExtra("stationSlug", stationSlug)
        Log.d("EPF", "$stationSlug")
        finish()
        startActivity(intent)
        //val findStation = FindStation()
        //findStation.resultQRCode()

        //val linesByStation: ArrayList<AllLines.Line> = getLinesByStation(view, lines, stationSlug)
        //val listPicto = getListPicto(view.context, linesByStation)
        //val stationName = getNameFromSlug(stationSlug, lines)
        //setAdapter(view, activity, stationName, linesByStation, listPicto)


        //startActivity(intent)
        /*val builder = AlertDialog.Builder(this)
        builder.setTitle("Result")
        builder.setPositiveButton("OK") {dialog, which ->
            var stationSlug = info?.get(0) + info?.get(1)
            Log.d("EPF", "$stationSlug")
            val intent= Intent(this, ScheduleActivity::class.java)
            intent.putExtra("station", stationSlug)
            //intent.putExtra("pictoline", pictoLine)
            //intent.putExtra("line", line)

            onRestart()
        }
        builder.setMessage(result)

        val alert = builder.create()
        alert.show()*/

    }

    private fun checkPermission() : Boolean{
        return ContextCompat.checkSelfPermission(this@QRCode,android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), REQUEST_CAMERA)
    }
}
