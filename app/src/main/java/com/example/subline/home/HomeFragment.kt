package com.example.subline.home

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.subline.MainActivity
import com.example.subline.R
import com.example.subline.find.Station
import com.example.subline.data.AppDatabase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {

        lateinit var mapFragment: SupportMapFragment
        lateinit var map: GoogleMap
        lateinit var fusedLocationClient: FusedLocationProviderClient
        lateinit var lastLocation: LatLng
        lateinit var favList : List<Station>
        lateinit var favListRecyclerView : RecyclerView
        lateinit var scheduleTextView : TextView
        lateinit var layout_nofavoris : RelativeLayout
        lateinit var listMarker: ArrayList<Marker>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

         val root = inflater.inflate(R.layout.fragment_home, container, false)

         scheduleTextView  = root.findViewById(R.id.nextScheduleTitle)
         layout_nofavoris = root.findViewById(R.id.layout_nofavoris)
         val layout_favoris = root.findViewById<LinearLayout>(R.id.layout_favoris)
        val Imageview_nofav = root.findViewById<ImageView>(R.id.noFavImage)
         favListRecyclerView = root.findViewById(R.id.favHomeRecyclerView)
         scheduleTextView.isVisible = false
         val favScheduleRecyclerView = root.findViewById<RecyclerView>(R.id.favScheduleRecyclerView)


         val database =
             Room.databaseBuilder(activity!!.baseContext, AppDatabase::class.java, "favoris")
                 .build()
         val favorisDao = database.getFavorisDao()

         runBlocking {
              favList = favorisDao.getStation()
         }
         fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
         mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
         listMarker = setUpMap(favList)

         favListRecyclerView.layoutManager =
             LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
         favScheduleRecyclerView.layoutManager =
             LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        Imageview_nofav.setOnClickListener {
            val intent = Intent(requireActivity().baseContext, MainActivity::class.java)
            intent.putExtra("switchView", 1)
            startActivity(intent)
        }

        runBlocking {
            if(favList.isEmpty()){
                layout_nofavoris.visibility = View.VISIBLE
                layout_favoris.visibility = View.GONE
            }else{
                layout_nofavoris.visibility = View.GONE
                layout_favoris.visibility = View.VISIBLE
                favListRecyclerView.adapter = FavorisAdapter(favList.toMutableList(), favScheduleRecyclerView, scheduleTextView,listMarker,layout_nofavoris)
            }
        }

         return root
    }


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private fun setUpMap(favList : List<Station>) : ArrayList<Marker>{
        var listMarker = arrayListOf<Marker>()
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return listMarker
        }
        mapFragment.getMapAsync {
            map = it
            fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->
                if (location != null) {
                    lastLocation = LatLng(location.latitude, location.longitude)
                    map.addMarker(
                        MarkerOptions()
                            .position(lastLocation)
                            .title("Vous êtes ici"))
                            .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))

                }

                favList.map {
                    val marker : Marker = map.addMarker(MarkerOptions()
                        .position(LatLng(it.latitude, it.longitude))
                        .title(it.name))
                    listMarker.add(marker)
                }
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.85830005598138, 2.347865087577108),10f))
            }
        }
        return listMarker
    }
}
