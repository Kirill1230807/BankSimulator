package com.example.banksimulator.data.repositoryImpl

import com.example.banksimulator.data.local.dao.AccountDao
import com.example.banksimulator.data.mapper.toDomain
import com.example.banksimulator.domain.model.Account
import com.example.banksimulator.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao
) : AccountRepository {
    override fun getUserWithAccounts(userId: String): Flow<List<Account>> {
        return accountDao.getUserWithAccounts(userId).map { userWithAccounts ->
            userWithAccounts.accounts.map { it.toDomain() }
        }
    }
}