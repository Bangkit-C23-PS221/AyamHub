package com.bangkit.ayamhub.helpers

import android.content.Context
import android.widget.Toast

object Reusable {
    fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun getCity(address: String): String {
        val cityName = address.split(", ")
        return cityName[1]
    }
}