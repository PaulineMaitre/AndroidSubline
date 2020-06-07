package com.example.subline.find

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        finish()
        startActivity(intent)
    }

    private fun checkPermission() : Boolean{
        return ContextCompat.checkSelfPermission(this@QRCode,android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), REQUEST_CAMERA)
    }
}
