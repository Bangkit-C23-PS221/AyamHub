package com.bangkit.ayamhub.ui.makefarm

import androidx.lifecycle.ViewModel
import com.bangkit.ayamhub.data.repository.FarmRepository

class MakeFarmViewModel(private val repository: FarmRepository) : ViewModel() {

    val provinceId = repository.provinsiId

    val kabupatenId = repository.kabupatenId

    val getProvinsi = repository.getProvinsi()

    val getKabupaten = repository.getKabupaten()

    val getKecamatan = repository.getKecamatan()

}