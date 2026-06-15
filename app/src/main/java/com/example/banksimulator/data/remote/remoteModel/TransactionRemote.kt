package com.example.banksimulator.data.remote.remoteModel

import com.google.firebase.firestore.DocumentId

data class TransactionRemote(
    @DocumentId val transactionId: String = "",
    val senderAccountId: String = "",
    val receiverAccountId: String = "",
    val name: String = "",
    val amount: String = "0.00",
    val description: String? = "",
    val currency: String = "",
    val status: String = "",
    val createdAt: Long = 0L
)
