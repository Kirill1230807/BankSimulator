package com.example.banksimulator.data.repositoryImpl

import com.example.banksimulator.data.local.dao.UserDao
import com.example.banksimulator.data.local.entity.UserEntity
import com.example.banksimulator.data.local.entity.foreignkeys.UserWithAccounts
import com.example.banksimulator.domain.repository.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val userDao: UserDao
) : UserRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("Користувача з таким email не існує."))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Невірний пароль або email"))
        } catch (e: Exception) {
            Result.failure(Exception("Помилка входу: ${e.localizedMessage}"))
        }
    }

    override suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        phoneNumber: String
    ): Result<Unit> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user

            if (firebaseUser != null) {
                val userEntity = UserEntity(
                    userId = firebaseUser.uid,
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    phoneNumber = phoneNumber,
                    createdAt = System.currentTimeMillis()
                )

                userDao.insertUser(userEntity)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Не вдалося отримати дані користувача з Firebase"))
            }
        } catch (e: FirebaseAuthWeakPasswordException) {
            Result.failure(Exception("Пароль занадто слабкий. Використовуйте мінімум 6 символів."))
        } catch (e: FirebaseAuthUserCollisionException) {
            Result.failure(Exception("Користувач з таким email вже зареєстрований."))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Невірний формат електронної пошти."))
        } catch (e: Exception) {
            Result.failure(Exception("Сталася помилка при реєстрації: ${e.localizedMessage}"))
        }
    }

    override fun getUserWithAccounts(userId: String): Flow<UserWithAccounts> {
        return userDao.getUserWithAccounts(userId)
    }

    override fun getCurrentUserData(): Flow<UserEntity?> {
        val currentUserId = auth.currentUser?.uid
        return if (currentUserId != null) {
            userDao.getUserById(currentUserId)
        } else {
            flowOf(null)
        }
    }

    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override suspend fun signOut() {
        auth.signOut()
    }

}