package com.example.banksimulator.di

import com.example.banksimulator.data.repositoryImpl.AccountRepositoryImpl
import com.example.banksimulator.data.repositoryImpl.TransactionRepositoryImpl
import com.example.banksimulator.data.repositoryImpl.UserRepositoryImpl
import com.example.banksimulator.domain.repository.AccountRepository
import com.example.banksimulator.domain.repository.TransactionRepository
import com.example.banksimulator.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindAccountRepository(
        accountRepositoryImpl: AccountRepositoryImpl
    ): AccountRepository

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        transactionRepositoryImpl: TransactionRepositoryImpl
    ): TransactionRepository
}