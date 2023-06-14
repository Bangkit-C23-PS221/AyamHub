package com.bangkit.ayamhub.helpers.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.ayamhub.data.repository.FarmRepository
import com.bangkit.ayamhub.data.repository.UserRepository
import com.bangkit.ayamhub.helpers.injection.Injection
import com.bangkit.ayamhub.ui.detail.DetailViewModel
import com.bangkit.ayamhub.ui.farmer.FarmerVIewModel
import com.bangkit.ayamhub.ui.homepage.bookmarks.BookmarksViewModel
import com.bangkit.ayamhub.ui.homepage.detection.DetectionViewModel
import com.bangkit.ayamhub.ui.homepage.home.HomeViewModel
import com.bangkit.ayamhub.ui.homepage.profile.ProfileViewModel
import com.bangkit.ayamhub.ui.login.LoginViewModel
import com.bangkit.ayamhub.ui.farmform.FarmFormViewModel
import com.bangkit.ayamhub.ui.register.RegisterViewModel
import com.bangkit.ayamhub.ui.splashscreen.SplashScreenViewModel

class ViewModelFactory(
    private val farmRepository: FarmRepository,
    private val userRepository: UserRepository
    ) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> return LoginViewModel(userRepository) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> return RegisterViewModel(userRepository) as T
            modelClass.isAssignableFrom(DetectionViewModel::class.java) -> return DetectionViewModel(farmRepository ,userRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> return HomeViewModel(farmRepository) as T
            modelClass.isAssignableFrom(BookmarksViewModel::class.java) -> return BookmarksViewModel(farmRepository) as T
            modelClass.isAssignableFrom(FarmFormViewModel::class.java) -> return FarmFormViewModel(farmRepository, userRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> return ProfileViewModel(userRepository) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> return DetailViewModel(userRepository, farmRepository) as T
            modelClass.isAssignableFrom(SplashScreenViewModel::class.java) -> return SplashScreenViewModel(userRepository) as T
            modelClass.isAssignableFrom(FarmerVIewModel::class.java) -> return FarmerVIewModel(farmRepository) as T

        }
        throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideFarmRepository(context),
                    Injection.provideUserRepository(context))
            }.also { instance = it }
    }
}