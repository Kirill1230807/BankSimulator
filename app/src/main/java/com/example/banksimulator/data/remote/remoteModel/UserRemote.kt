package com.example.banksimulator.data.remote.remoteModel

import com.google.firebase.firestore.DocumentId

data class UserRemote(
    @DocumentId val userId: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val createdAt: Long = 0L
)
