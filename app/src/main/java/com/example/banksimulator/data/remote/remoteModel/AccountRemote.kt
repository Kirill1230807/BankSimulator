package com.example.banksimulator.data.remote.remoteModel

import com.google.firebase.firestore.DocumentId


data class AccountRemote(
    @DocumentId val accountId: String = "",
    val ownerId: String = "",
    val accountType: String = "",
    val fullName: String = "",
    val iban: String = "",
    val balance: String = "0.00",
    val currency: String = "",
    val status: String = "",
    val createdAt: Long = 0L
)
