package com.bangkit.ayamhub.helpers

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.bangkit.ayamhub.data.network.response.LocationResponse


fun getSelectedItem(spinner: Spinner): String {
    return if (spinner.selectedItem != null) {
        if (spinner.selectedItem.toString().contains("Pilih")) {
            ""
        } else {
            spinner.selectedItem.toString()
        }
    } else {
        ""
    }
}

fun onItemSelected(
    onClick: (position: Int) -> Unit
) = object : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        onClick(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}

fun provideArrayAdapter(context: Context): ArrayAdapter<String> {
    val adapter = ArrayAdapter<String>(
        context,
        android.R.layout.simple_spinner_item
    )
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    return adapter
}

fun setupDropdown(
    list: List<LocationResponse>,
    adapter: ArrayAdapter<String>,
    place: String
): List<LocationResponse>{

    val sortedData = list.sortedBy { it.nama }
    adapter.add("Pilih $place")
    adapter.addAll(sortedData.map { it.nama })
    adapter.notifyDataSetChanged()
    return sortedData
}