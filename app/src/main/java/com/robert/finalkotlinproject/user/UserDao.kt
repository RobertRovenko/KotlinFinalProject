package com.robert.finalkotlinproject.user

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)

    @Query("SELECT * FROM User")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    fun getUsersFlow(username: String, password: String): Flow<List<User>>

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): User?

    @Delete
    fun deleteUser(user: User)

    @Query("UPDATE user SET password = :newPassword WHERE username = :username")
    fun updateUserPassword(username: String, newPassword: String)

    @Query("DELETE FROM user WHERE username = :username")
    suspend fun deleteUserByUsername(username: String)

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUserByUsername(username: String): User

}