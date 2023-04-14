package com.robert.finalkotlinproject.user

import com.robert.finalkotlinproject.AppDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class UserRepository (private val appDatabase : AppDatabase, private val
coroutineScope : CoroutineScope
) {
    fun insertUser(user: User) {
        appDatabase .userDao().insertUser(user)
    }
    fun getUsers(username: String, password: String): List<User> {
        return appDatabase .userDao().getAllUsers ()
    }
    fun performDatabaseOperation (dispatcher: CoroutineDispatcher,
                                  databaseOperation : suspend () -> Unit) {
        coroutineScope .launch(dispatcher) {
            databaseOperation ()
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