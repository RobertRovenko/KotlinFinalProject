package com.robert.finalkotlinproject.user

import com.robert.finalkotlinproject.AppDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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


}