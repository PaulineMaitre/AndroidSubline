package com.example.subline.ui.find.metros

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.service.RatpPictoService
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_PICTO
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.retrofit
import com.pixplicity.sharp.Sharp
import com.example.subline.R
import com.example.subline.ui.find.metros.AllMetrosAdapter
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.retrofit
import kotlinx.android.synthetic.main.activity_appel_api.*
import kotlinx.android.synthetic.main.fragment_find_metros.*
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream

/**
 * A simple [Fragment] subclass.
 */
class FindMetros : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_find_metros, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.find_all_metro_rv)
        val rv2 = view.findViewById<RecyclerView>(R.id.find_all_stations_rv)

        // utilise l'API
        //var listmetros = listOf<String>("M1","M2","M3","M3bis","M4","M5","M6","M7","M7bis","M8","M9","M10","M11","M12")
        // utilise les drawables
        //var listmetros = listOf<Int>(R.drawable.m1,
                             //       R.drawable.m2,
                                  //  R.drawable.m3,
                                  //  R.drawable.m3b,
                                  //  R.drawable.m4,
                                  //  R.drawable.m5,
                                  //  R.drawable.m6,
                                  //  R.drawable.m7,
                                 //   R.drawable.m7b,
                                 //   R.drawable.m8,
                                  //  R.drawable.m9,
                                 //   R.drawable.m10,
                                //    R.drawable.m11,
                                  //  R.drawable.m12,
                                  //  R.drawable.m13,
                                  //  R.drawable.m14,
                                  //  R.drawable.orlyval,
                                 //   R.drawable.mfun)

        var listmetros = listOf<String>("1","2","3","3bis","4","5","6","7","7bis","8","9","10","11","12","13","14","Orlyval","fun")
        rv.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        rv2.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        rv.adapter = AllMetrosAdapter(listmetros,rv2)
        return view
    }

}

