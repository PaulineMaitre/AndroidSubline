package com.example.subline.data

import android.app.Application
import com.facebook.stetho.Stetho

class SublineApp: Application()  {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}