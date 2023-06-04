package com.bangkit.ayamhub.ui.detail

import androidx.lifecycle.ViewModel
import com.bangkit.ayamhub.data.repository.FarmRepository
import com.bangkit.ayamhub.data.repository.UserRepository

class DetailViewModel(
    private val userRepo: UserRepository,
    private val farmRepo: FarmRepository
) : ViewModel() {

    fun getFarmDetail(id: Int) = farmRepo.getFarmDetail(id)

    fun addBookmark(id: Int) = farmRepo.addBookmark(id)

    fun removeBookmark(id: Int) = farmRepo.removeBookmark(id)

    fun checkBookmark(id: Int) = farmRepo.checkBookmark(id)

}