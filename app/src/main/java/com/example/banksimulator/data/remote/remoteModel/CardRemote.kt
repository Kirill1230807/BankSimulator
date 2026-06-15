package com.example.banksimulator.data.remote.remoteModel

import com.google.firebase.firestore.DocumentId

data class CardRemote(
    @DocumentId val cardId: String = "",
    val accountOwnerId: String = "",
    val cardNumber: String = "",
    val cardColor: Long = 0L,
    val cardHolderName: String = "",
    val expirationDate: String = "",
    val status: String = "",
    val createdAt: Long = 0
)
