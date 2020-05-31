package com.example.subline.utils

import com.example.subline.R

const val BASE_URL_TRANSPORT = "https://api-ratp.pierre-grimaud.fr/"
const val BASE_URL_PICTO = "https://data.ratp.fr/"

const val TYPE_METRO = "metros"
const val TYPE_RER = "rers"
const val TYPE_TRAM = "tramways"
const val TYPE_BUS = "buses"
const val TYPE_NOCTI = "noctiliens"

val PICTO_METRO = listOf(R.drawable.m1, R.drawable.m2, R.drawable.m3, R.drawable.m3b, R.drawable.m4, R.drawable.m5,
    R.drawable.m6, R.drawable.m7, R.drawable.m7b, R.drawable.m8, R.drawable.m9, R.drawable.m10, R.drawable.m11,
    R.drawable.m12, R.drawable.m13, R.drawable.m14, R.drawable.mfun, R.drawable.orlyval)

val PICTO_RER = listOf(R.drawable.rera, R.drawable.rerb, R.drawable.rerc, R.drawable.rerd, R.drawable.rere)

var PICTO_TRAM = listOf(R.drawable.t1, R.drawable.t2, R.drawable.t3a, R.drawable.t3b, R.drawable.t4,
    R.drawable.t5, R.drawable.t6, R.drawable.t7, R.drawable.t8, R.drawable.t11)

var PICTO_NOCTI = listOf(R.drawable.n01, R.drawable.n02, R.drawable.n11, R.drawable.n12,
    R.drawable.n13, R.drawable.n14, R.drawable.n15, R.drawable.n16, R.drawable.n21, R.drawable.n22,
    R.drawable.n23, R.drawable.n24, R.drawable.n31, R.drawable.n32, R.drawable.n33, R.drawable.n34,
    R.drawable.n35, R.drawable.n41, R.drawable.n42, R.drawable.n43, R.drawable.n44, R.drawable.n45,
    R.drawable.n51, R.drawable.n52, R.drawable.n53, R.drawable.n61, R.drawable.n62, R.drawable.n63,
    R.drawable.n66, R.drawable.n71, R.drawable.n122, R.drawable.n153)

val STATIONS_RER_C = listOf("Ablon", "Arpajon", "Athis-Mons", "Avenue du Président-Kennedy", "Avenue Foch", "Avenue Henri Martin",
    "Bibliothèque François-Mittérand", "Bièvres", "Boulainvilliers", "Bourray", "Brétigny", "Breuillet - Bruyères-le-Chatel",
    "Breuillet - Village", "Cernay", "Chamarande", "Champ de Mars - Tour Eiffel", "Chaville - Vélizy", "Chemin d'Antony",
    "Chilly-Mazarin", "Choisy-le-Roi", "Dourdan", "Dourdan - La Forêt", "Egly", "Epinay-sur-Orge", "Epinay-sur-Seine",
    "Ermont-Eaubonne", "Etampes", "Etrechy", "Franconville - Le Plessis-Bouchard", "Gare d'Austerlitz", "Gennevilliers",
    "Gravigny-Balizy", "Igny", "Invalides", "Issy", "Issy - Val de Seine", "Ivry-sur-Seine", "Javel", "Jouy-en-Josas",
    "Juvisy", "Lardy", "La Norville - Saint-Germain-lès-Arpajon", "Les Ardoines", "Les Grésillons", "Les Saules",
    "Longjumeau", "Marolles-en-Hurepoix", "Massy - Palaiseau", "Massy - Verrières", "Meudon-Val-Fleury",
    "Montigny-Beauchamp", "Musée d'Orsay", "Neuilly-Pore Maillot", "Orly-Ville", "Pereire-Levallois",
    "Petit Jouy - Les Loges", "Petit Vaux", "Pont de l'Alma", "Pont de Rungis - Aéroport d'Orly",
    "Pont du Garigliano", "Pontoise", "Porchefontaine", "Porte de Clichy", "Rungis - La Fraternelle",
    "Saint-Chéron", "Saint-Cyr", "Saint-Gratien", "Saint-Martin-d'Etampes", "Saint-Michel - Notre-Dame",
    "Saint-Michel-sur-Orge", "Saint-Ouen", "Saint-Ouen l'Aumône-Liesse", "Saint-Quentin-en-Yvelines - Montigny-le-Bretonneux",
    "Sainte-Geneviève-des-Bois", "Savigny-sur-Orge", "Sermaise", "Vauboven", "Versailles-Chantiers",
    "Versailles-Chateau-Rive-Gauche", "Villeneuve-le-Roi", "Viroflay-Rive-Gauche", "Vitry-sur-Seine")

val STATIONS_RER_D =  listOf("Ballancourt", "Boigneville", "Boissise-le-Roi", "Boutigny", "Boussy-Saint-Antoine", "Buno - Gironville",
    "Brunoy", "Cesson", "Chantilly - Gouvieux", "Châtelet - Les Halles", "Combs-la-Ville - Quincy", "Corbeilles-Essonnes",
    "Creil", "Créteil-Pompadour", "Essonnes - Robinson", "Evry - Courcouronnes Centre", "Evry-Val-de-Seine", "Gare de Lyon",
    "Gare du Nord", "Garges - Sarcelles", "Goussainville", "Grand Bourg", "Grigny-Centre", "Juvisy", "La Borne Blanche",
    "La Ferté-Alais", "Le Bras de Fer - Evry - Génopole", "Le Mée", "Le Coudray-Montceaux", "Le Plessis-Chenet",
    "Le Vert de Maisons", "Les Noues", "Lieusaint - Moissy", "Louvres", "Maisse", "Maisons-Alfort - Alfortville",
    "Malesherbes", "Melun", "Mennecy", "Montgeron - Crosne", "Moulin-Galant", "Orangis - Bois de l'Epine",
    "Orry-la-Ville - Coye", "Pierrefitte - Stains", "Ponthierry - Pringy", "Ris-Orangis", "Saint-Denis", "Saint-Fargeau",
    "Savigny-le-Temple - Nandy", "Stade de France - Saint-Denis", "Survilliers - Fosses", "Vigneux-sur-Seine", "Villabé",
    "Villeneuve-Saint-Georges", "Villeneuve-Triage", "Villiers-le-Bel - Gonesse - Arnouville", "Viry-Châtillon", "Vosves", "Yerres")

