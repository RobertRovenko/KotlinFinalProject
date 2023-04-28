package com.robert.finalkotlinproject.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

data class User(
    var username: String,
    val password: String,

    ) {

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

}