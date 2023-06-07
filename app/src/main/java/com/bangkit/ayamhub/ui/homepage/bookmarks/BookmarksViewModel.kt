package com.bangkit.ayamhub.ui.homepage.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.ayamhub.data.network.response.BookmarkResponse
import com.bangkit.ayamhub.data.repository.FarmRepository
import com.bangkit.ayamhub.data.repository.UserRepository
import com.bangkit.ayamhub.data.network.Result

class BookmarksViewModel(
    private val farmRepository: FarmRepository
) : ViewModel() {

    fun getAllBookmark(): LiveData<Result<List<BookmarkResponse>>> {
        return farmRepository.getAllBookmark()
    }

}