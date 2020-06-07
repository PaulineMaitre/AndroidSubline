package com.example.subline.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.*

class DatabaseJSON {

    fun save(context: Context?, fileName: String, objectSave: Any?){

        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()

        val objectSaveConvert = objectSave

        val objectSaveJson = gson.toJson(objectSaveConvert)
        val myExternalFile: File = File(context?.filesDir,fileName)
        try {
            val fileOutPutStream = FileOutputStream(myExternalFile)
            fileOutPutStream.write(objectSaveJson.toByteArray())
            fileOutPutStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun <T> load(context: Context?, fileName : String, classOfT: Class<T>?): T {
        val gson = Gson()
        var myExternalFile = File(context?.filesDir,fileName)

        val fileInputStream = FileInputStream(myExternalFile)
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine(); text }() != null) {
            stringBuilder.append(text)
        }
        fileInputStream.close()

        return gson.fromJson(stringBuilder.toString(), classOfT)
    }

}