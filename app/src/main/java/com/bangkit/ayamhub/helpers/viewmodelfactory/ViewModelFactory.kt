package com.bangkit.ayamhub.helpers.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.ayamhub.data.repository.FarmRepository
import com.bangkit.ayamhub.data.repository.UserRepository
import com.bangkit.ayamhub.helpers.injection.Injection
import com.bangkit.ayamhub.ui.homepage.ui.bookmarks.BookmarksViewModel
import com.bangkit.ayamhub.ui.homepage.ui.detection.DetectionViewModel
import com.bangkit.ayamhub.ui.homepage.ui.home.HomeViewModel
import com.bangkit.ayamhub.ui.login.LoginViewModel
import com.bangkit.ayamhub.ui.makefarm.MakeFarmViewModel
import com.bangkit.ayamhub.ui.register.RegisterViewModel

class ViewModelFactory(
    private val farmRepository: FarmRepository,
    private val userRepository: UserRepository
    ) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> return LoginViewModel(userRepository) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> return RegisterViewModel(userRepository) as T
            modelClass.isAssignableFrom(DetectionViewModel::class.java) -> return DetectionViewModel(farmRepository ,userRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> return HomeViewModel(farmRepository ,userRepository) as T
            modelClass.isAssignableFrom(BookmarksViewModel::class.java) -> return BookmarksViewModel(farmRepository ,userRepository) as T
            modelClass.isAssignableFrom(MakeFarmViewModel::class.java) -> return MakeFarmViewModel(farmRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideFarmRepository(),
                    Injection.provideUserRepository(context))
            }.also { instance = it }
    }
}