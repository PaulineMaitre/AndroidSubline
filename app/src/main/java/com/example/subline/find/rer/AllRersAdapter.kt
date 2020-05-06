package com.example.subline.find.rer

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.service.RatpPictoService
import com.example.subline.service.RatpService
import com.example.subline.utils.*
import com.pixplicity.sharp.Sharp
import kotlinx.android.synthetic.main.list_metro_item.view.*
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream

class AllRersAdapter (val rers: List<String>, var stations: RecyclerView) : RecyclerView.Adapter<AllRersAdapter.RersViewHolder>() {

        class RersViewHolder(val rersView: View) : RecyclerView.ViewHolder(rersView)
        val pictoRers = listOf<Int>(
            R.drawable.rera,
            R.drawable.rerb,
            R.drawable.rerc,
            R.drawable.rerc,
            R.drawable.rerc,
            R.drawable.rerc,
            R.drawable.rerc,
            R.drawable.rerc,
            R.drawable.rerc,
            R.drawable.rerc,
            R.drawable.rerc,
            R.drawable.rerc,
            R.drawable.rerc,
            R.drawable.rerd,
            R.drawable.rerd,
            R.drawable.rerd,
            R.drawable.rerd,
            R.drawable.rerd,
            R.drawable.rere
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RersViewHolder {
            val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
            val view: View = layoutInfater.inflate(R.layout.list_metro_item, parent,false)

            return RersViewHolder(view)
        }

        override fun getItemCount(): Int = rers.size


        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: RersViewHolder, position: Int) {
            var rer = rers[position]
            holder.rersView.lineName.setImageResource(pictoRers[position])

            holder.rersView.setOnClickListener {
                var liststations: List<String> = emptyList()
                if(rer == "C" || rer == "D") {
                    if(rer == "C"){
                        liststations = listOf("Pontoise",
                            "Saint-Ouen l'Aumône-Liesse", "Montigny-Beauchamp",
                            "Franconville - Le Plessis-Bouchard", "Cernay",
                            "Ermont-Eaubonne", "Saint-Gratien",
                            "Epinay-sur-Seine", "Gennevilliers",
                            "Les Grésillons", "Saint-Ouen",
                            "Porte de Clichy", "Pereire-Levallois",
                            "Neuilly-Pore Maillot", "Avenue Foch",
                            "Avenue Henri Martin", "Boulainvilliers",
                            "Avenue du Président-Kennedy", "Saint-Quentin-en-Yvelines - Montigny-le-Bretonneux",
                            "Saint-Cyr", "Versailles-Chantiers",
                            "Versailles-Chateau-Rive-Gauche", "Porchefontaine",
                            "Viroflay-Rive-Gauche", "Chaville - Vélizy",
                            "Meudon-Val-Fleury", "Issy",
                            "Issy - Val de Seine", "Pont du Garigliano",
                            "Javel", "Champ de Mars - Tour Eiffel",
                            "Pont de l'Alma", "Invalides",
                            "Musée d'Orsay", "Saint-Michel - Notre-Dame",
                            "Gare d'Austerlitz", "Bibliothèque François-Mittérand",
                            "Ivry-sur-Seine", "Vitry-sur-Seine",
                            "Les Ardoines", "Petit Jouy - Les Loges",
                            "Jouy-en-Josas", "Vauboven",
                            "Bièvres", "Igny",
                            "Massy - Palaiseau", "Massy - Verrières",
                            "Chemin d'Antony", "Rungis - La Fraternelle",
                            "Pont de Rungis - Aéroport d'Orly",
                            "Orly-Ville", "Les Saules",
                            "Choisy-le-Roi", "Villeneuve-le-Roi",
                            "Ablon", "Athis-Mons",
                            "Juvisy", "Longjumeau",
                            "Chilly-Mazarin", "Gravigny-Balizy",
                            "Petit Vaux", "Savigny-sur-Orge",
                            "Epinay-sur-Orge", "Sainte-Geneviève-des-Bois",
                            "Saint-Michel-sur-Orge", "Brétigny",
                            "La Norville - Saint-Germain-lès-Arpajon",
                            "Arpajon", "Egly",
                            "Breuillet - Bruyères-le-Chatel", "Breuillet - Village",
                            "Saint-Chéron", "Sermaise",
                            "Dourdan", "Dourdan - La Forêt",
                            "Marolles-en-Hurepoix", "Bourray",
                            "Lardy", "Chamarande",
                            "Etrechy", "Etampes", "Saint-Martin-d'Etampes")
                    } else {
                        liststations = listOf("Creil", "Chantilly - Gouvieux",
                            "Orry-la-Ville - Coye", "La Borne Blanche",
                            "Survilliers - Fosses", "Louvres",
                            "Les Noues", "Goussainville",
                            "Villiers-le-Bel - Gonesse - Arnouville", "Garges - Sarcelles",
                            "Pierrefitte - Stains", "Saint-Denis",
                            "Stade de France - Saint-Denis", "Gare du Nord",
                            "Châtelet - Les Halles", "Gare de Lyon",
                            "Maisons-Alfort - Alfortville", "Le Vert de Maisons",
                            "Créteil-Pompadour", "Villeneuve-Triage",
                            "Villeneuve-Saint-Georges", "Montgeron - Crosne",
                            "Yerres", "Brunoy",
                            "Boussy-Saint-Antoine", "Combs-la-Ville - Quincy",
                            "Lieusaint - Moissy", "Savigny-le-Temple - Nandy",
                            "Cesson", "Le Mée",
                            "Vgneux-sur-Seine", "Juvisy",
                            "Viry-Châtillon", "Ris-Orangis",
                            "Grand Bourg", "Evry-Val-de-Seine",
                            "Grigny-Centre", "Orangis - Bois de l'Epine",
                            "Evry - Courcouronnes Centre", "Le Bras de Fer - Evry - Génopole",
                            "Corbeilles-Essonnes", "Essonnes - Robinson",
                            "Villabé", "Le Plessis-Chenet",
                            "Le Coudray-Montceaux", "Saint-Fargeau",
                            "Ponthierry - Pringy", "Boissise-le-Roi",
                            "Vosves", "Melun",
                            "Moulin-Galant", "Mennecy",
                            "Ballancourt", "La Ferté-Alais",
                            "Boutigny", "Maisse",
                            "Buno - Gironville",
                            "Boigneville", "Malesherbes")
                    }
                } else {
                    liststations = affiche_list_stations(rer)
                }

                stations.adapter = AllRerStationsAdapter(liststations, pictoRers[position], rer)
            }

        }

        fun affiche_list_stations(rer: String) : List<String>{
            var liststations = arrayListOf<String>()
            val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
            runBlocking {
                val results = service.getStations(TYPE_RER, rer)
                results.result.stations.map {
                    liststations.add(it.name)
                }
            }

            return liststations
        }





        /*private fun getLinePicto(lineId: String, imageview: ImageView) {

            val pictoService = retrofit(BASE_URL_PICTO).create(RatpPictoService::class.java)
            runBlocking {
                val pictoResult = pictoService.getPictoInfo(lineId)
                Log.d("EPF", "test $pictoResult")
                val id = pictoResult.records[0].fields.noms_des_fichiers.id
                val result = pictoService.getImage(id)
                result.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        val stream: InputStream = response.body()!!.byteStream()
                        Sharp.loadInputStream(stream).into(imageview)
                        // stream.close()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
            }
        }*/


    }
