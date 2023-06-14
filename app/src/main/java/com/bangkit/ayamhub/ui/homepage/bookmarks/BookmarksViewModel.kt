package com.bangkit.ayamhub.ui.homepage.bookmarks

import androidx.lifecycle.ViewModel
import com.bangkit.ayamhub.data.repository.FarmRepository
import com.bangkit.ayamhub.data.repository.UserRepository

class BookmarksViewModel(
    private val farmRepository: FarmRepository
) : ViewModel() {

    fun getAllBookmark() = farmRepository.getAllBookmark()
}