package com.example.banksimulator.data.mapper

import com.example.banksimulator.data.local.entity.UserEntity
import com.example.banksimulator.data.remote.remoteModel.UserRemote
import com.example.banksimulator.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        userId = this.userId,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        phoneNumber = this.phoneNumber,
        createdAt = this.createdAt
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        userId = this.userId,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        phoneNumber = this.phoneNumber,
        createdAt = this.createdAt
    )
}

fun UserRemote.toEntity(): UserEntity {
    return UserEntity(
        userId = this.userId,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        phoneNumber = this.phoneNumber,
        createdAt = this.createdAt
    )
}

fun UserEntity.toRemote(): UserRemote {
    return UserRemote(
        userId = this.userId,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        phoneNumber = this.phoneNumber,
        createdAt = this.createdAt
    )
}