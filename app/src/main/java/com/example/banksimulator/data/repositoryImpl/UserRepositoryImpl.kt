package com.example.banksimulator.data.repositoryImpl

import android.util.Log
import com.example.banksimulator.data.local.dao.TransactionDao
import com.example.banksimulator.data.local.dao.UserDao
import com.example.banksimulator.data.local.entity.UserEntity
import com.example.banksimulator.data.mapper.toDomain
import com.example.banksimulator.domain.model.Account
import com.example.banksimulator.domain.model.Transaction
import com.example.banksimulator.domain.model.User
import com.example.banksimulator.domain.repository.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val userDao: UserDao,
    private val transactionDao: TransactionDao
) : UserRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (_: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("Користувача з таким email не існує."))
        } catch (_: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Невірний пароль або email"))
        } catch (e: Exception) {
            Result.failure(Exception("Помилка входу: ${e.localizedMessage}"))
        }
    }

    override suspend fun signUpWithEmail(
        email: String,
        password: String,
    ): String {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        return authResult.user?.uid ?: throw Exception("Не вдалося отримати UID користувача")
    }

    override suspend fun saveUserProfile(user: User) {
        withContext(Dispatchers.IO) {
            try {
                Log.d("RegisterBank", "1. Готуємо дані для Firestore...")
                val userMap = hashMapOf(
                    "id" to user.userId,
                    "firstName" to user.firstName,
                    "lastName" to user.lastName,
                    "email" to user.email,
                    "phoneNumber" to user.phoneNumber,
                    "createdAt" to System.currentTimeMillis()
                )

                Log.d("RegisterBank", "2. Відправляємо дані у Firestore...")
                firestore.collection("users")
                    .document(user.userId)
                    .set(userMap)
                    .await()

                Log.d("RegisterBank", "3. Firestore успіх! Зберігаємо в Room...")
                val userEntity = UserEntity(
                    userId = user.userId,
                    firstName = user.firstName,
                    lastName = user.lastName,
                    email = user.email,
                    phoneNumber = user.phoneNumber,
                    createdAt = System.currentTimeMillis()
                )

                userDao.insertUser(userEntity)
                Log.d("RegisterBank", "4. Room успішно зберіг дані! Все готово.")
            } catch (e: Exception) {
                Log.e("RegisterBank", "Помилка всередині saveUserProfile: ${e.message}", e)
                throw e
            }
        }
    }

    override fun getUserWithAccounts(userId: String): Flow<List<Account>> {
        return userDao.getUserWithAccounts(userId).map { userWithAccounts ->
            userWithAccounts.accounts.map { it.toDomain() }
        }
    }

    override fun getCurrentUserData(): Flow<User?> {
        val currentUserId = auth.currentUser?.uid
        return if (currentUserId != null) {
            userDao.getUserById(currentUserId).map { it?.toDomain() }
        } else {
            flowOf(null)
        }
    }

    override fun getHomeUserData(userId: String): Flow<User?> {
        return userDao.getHomeUserData(userId).map { it?.user?.toDomain() }
    }

    override fun getUserTransactions(userId: String): Flow<List<Transaction>> {
        return transactionDao.getTransactionsForUser(userId).map { transactions ->
            transactions.map { it.toDomain() }
        }
    }

    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override suspend fun signOut() {
        auth.signOut()
    }

}