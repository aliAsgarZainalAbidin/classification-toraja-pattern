package com.devalid.yolov8tflite.util

import android.app.Activity
import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import java.io.InputStream

fun Activity.showToast(msg : String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

suspend fun readJsonFromRaw(context: Context, resourceId: Int): String? {
    return withContext(Dispatchers.IO) {
        try {
            val inputStream: InputStream = context.resources.openRawResource(resourceId)
            inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

fun jsonToMap(jsonString: String): Map<String, String> {
    val resultMap = mutableMapOf<String, String>()
    try {
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val nama = jsonObject.getString("nama")
            val deskripsi = jsonObject.getString("deskripsi")
            resultMap[nama] = deskripsi
        }
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    return resultMap
}