package com.robert.finalkotlinproject.user

import com.robert.finalkotlinproject.AppDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class UserRepository(private val appDatabase: AppDatabase, private val coroutineScope: CoroutineScope) {

    //check if a user with the same username already exists
    suspend fun getUserByUsername(username: String): User? {
        return withContext(Dispatchers.IO) {
            appDatabase.userDao().getUserByUsername(username)
        }
    }

    //modify the insertUser() function to first check if a user with the same username already exists
    suspend fun insertUser(user: User): Boolean {
        val existingUser = getUserByUsername(user.username)
        return if (existingUser == null) {
            withContext(Dispatchers.IO) {
                appDatabase.userDao().insertUser(user)
            }
            true // Return true to indicate that the user was successfully inserted
        } else {
            false // Return false to indicate that a user with the same username already exists
        }
    }


    fun getUsers(username: String, password: String): List<User> {
        return appDatabase.userDao().getAllUsers()
    }

    fun performDatabaseOperation(dispatcher: CoroutineDispatcher, databaseOperation: suspend () -> Unit) {
        coroutineScope.launch(dispatcher) {
            databaseOperation()
        }
    }

    fun getUsersFlow(username: String, password: String): Flow<List<User>> {
        return appDatabase.userDao().getUsersFlow(username, password)
    }

    suspend fun updateUserPassword(username: String, newPassword: String) {
        withContext(Dispatchers.IO) {
            appDatabase.userDao().updateUserPassword(username, newPassword)
        }
    }

    suspend fun deleteUserByUsername(username: String) {
        withContext(Dispatchers.IO) {
            appDatabase.userDao().deleteUserByUsername(username)
        }
    }
}
