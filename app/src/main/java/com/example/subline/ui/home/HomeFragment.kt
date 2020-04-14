package com.example.subline.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.subline.AppelApi
import com.example.subline.FindActivity
import com.example.subline.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val bt_api: Button = root.findViewById(R.id.bt_api)

        bt_api.setOnClickListener { view ->
            val intent = Intent(this.context, AppelApi::class.java)
            startActivity(intent)
            true
        }

        val bt_find: Button = root.findViewById(R.id.bt_find)

        bt_find.setOnClickListener { view ->
            val intent = Intent(this.context, FindActivity::class.java)
            startActivity(intent)
            true
        }

        return root
    }
}
