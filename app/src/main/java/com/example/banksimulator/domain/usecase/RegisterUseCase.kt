package com.example.banksimulator.domain.usecase

import android.util.Log
import com.example.banksimulator.domain.model.User
import com.example.banksimulator.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        phoneNumber: String
    ): Result<Unit> {
        return try {
            val uid = userRepository.signUpWithEmail(email = email, password = password)
            val newUser = User(
                userId = uid,
                firstName = firstName,
                lastName = lastName,
                email = email,
                phoneNumber = phoneNumber
            )

            Log.d("RegisterBank", "Firebase Auth успіх! Пробуємо зберегти в Room...")
            userRepository.saveUserProfile(newUser)
            Log.d("RegisterBank", "Room успішно зберіг профайл!")

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}