package com.karl.demo.demo.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*


data class LoginResult(
    var token: String? = null,
    var isSuccess: Boolean = true,
    var message: String? = "",
    var user: User? = null
)

@Entity(tableName = "user")
@Parcelize
data class User(
    @PrimaryKey
    @NonNull
    var pid: String = UUID.randomUUID().toString(),
    var userName: String? = null,
    var nickname: String? = null,
    var password: String? = null,
    var sex: String? = null,
    var phone: String? = null,
    var email: String? = null,
    var photograph: String? = null,
    var flag: String? = null
): Parcelable

data class CommonEntity(var data: String? = null, var flag: Boolean = false)