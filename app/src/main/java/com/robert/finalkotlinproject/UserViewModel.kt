package com.robert.finalkotlinproject

import android.app.Application
import androidx.lifecycle.*
import com.robert.finalkotlinproject.user.User
import com.robert.finalkotlinproject.user.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepository = UserRepository(AppDatabase.getInstance(application), viewModelScope)
    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            userRepository.getUsersFlow(username, password).collect { userList ->
                val isUserValid = userList.any { it.username == username && it.password == password }
                _isLoggedIn.postValue(isUserValid)
            }
        }
    }

    fun signUpUser(username: String, password: String) {
        viewModelScope.launch {
            userRepository.insertUser(User(username, password))
        }
        _isLoggedIn.postValue(true)
    }

    fun logoutUser() {
        _isLoggedIn.postValue(false)
    }
}