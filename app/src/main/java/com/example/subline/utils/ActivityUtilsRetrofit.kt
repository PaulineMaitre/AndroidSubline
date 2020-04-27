package com.example.subline.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.ui.find.metros.AllMetrosAdapter
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun AppCompatActivity.retrofit(baseUrl: String) : Retrofit {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addNetworkInterceptor(StethoInterceptor())
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()
}

fun Fragment.retrofit(baseUrl: String) : Retrofit {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addNetworkInterceptor(StethoInterceptor())
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()
}

fun RecyclerView.Adapter<AllMetrosAdapter.MetrosViewHolder>.retrofit(baseUrl: String) : Retrofit {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addNetworkInterceptor(StethoInterceptor())
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()
}

