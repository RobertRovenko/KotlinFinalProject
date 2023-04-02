package com.robert.finalkotlinproject.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    fun insertUser(user:User)

    @Query("SELECT * FROM User")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    fun getUsersFlow(username: String, password: String): Flow<List<User>>

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): User?

}

