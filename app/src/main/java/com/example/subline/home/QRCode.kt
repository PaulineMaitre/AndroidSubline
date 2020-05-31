package com.example.subline.home

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.subline.find.findResults.ScheduleActivity
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
        val result =  p0?.text
        var info = result?.split("-")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Result")
        builder.setPositiveButton("OK") {dialog, which ->
           var line = info?.get(0)
           var pictoLine = info?.get(2)?.toInt()
           var stationName = info?.get(1)
            val intent= Intent(this, ScheduleActivity::class.java)
            intent.putExtra("station", stationName)
            intent.putExtra("pictoline", pictoLine)
            intent.putExtra("line", line)
            startActivity(intent)
            onRestart()
        }
        builder.setMessage(result)

        val alert = builder.create()
        alert.show()

    }

    private fun checkPermission() : Boolean{
        return ContextCompat.checkSelfPermission(this@QRCode,android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),REQUEST_CAMERA)
    }
}
