package com.bangkit.ayamhub.helpers.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.ayamhub.data.repository.FarmRepository
import com.bangkit.ayamhub.data.repository.UserRepository
import com.bangkit.ayamhub.helpers.injection.Injection

class ViewModelFactory(
    private val farmRepository: FarmRepository,
    private val userRepository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
//            modelClass.isAssignableFrom(LoginViewModel::class.java) -> return LoginViewModel(repository) as T
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