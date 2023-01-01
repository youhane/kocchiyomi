package com.example.kocchiyomi.data.firebase.model

import com.example.kocchiyomi.data.model.User
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName

@Suppress("ClassName")
@IgnoreExtraProperties
data class _User(
    @get:PropertyName("uid") @set:PropertyName("uid")
    var uid: String,
    @get:PropertyName("username") @set:PropertyName("username")
    var userName: String,
    @get:PropertyName("email") @set:PropertyName("email")
    var email: String,
) {
    @Suppress("unused")
    constructor() : this("", "", "")

    fun toDomain() = User(
        uid = uid,
        userName = userName,
        email = userName
    )
}