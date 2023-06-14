package com.bangkit.ayamhub.helpers

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.bangkit.ayamhub.data.network.Result
import com.bangkit.ayamhub.data.network.response.MessageResponse
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object Reusable {
    fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun splitAddress(address: String): List<String> {
        return address.split(", ", limit = 4)
    }

    fun getCity(address: String): String {
        return try {
            splitAddress(address)[1]
        } catch (e:Exception) {
            ""
        }

    }

    fun getProvince(address: String): String {
        return try {
            splitAddress(address)[0]
        } catch (e:Exception) {
            ""
        }
    }

    fun getDistrict(address: String): String {
        return try {
            splitAddress(address)[2]
        } catch (e:Exception) {
            ""
        }
    }

    fun getSpecificAddress(address: String): String {
        return splitAddress(address)[3]
    }

    fun getChickenAge(date: String): String {
        val actualDate = date.split("T")
        val chickenDate = LocalDate.parse(actualDate[0])
        val currentDate = LocalDate.now()
        return ChronoUnit.DAYS.between(chickenDate, currentDate).toString()
    }

    fun ageToDate(days: Int): String {
        val currentDate = LocalDate.now()
        val birthDate = currentDate.minusDays(days.toLong())
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return birthDate.format(formatter)
    }
}