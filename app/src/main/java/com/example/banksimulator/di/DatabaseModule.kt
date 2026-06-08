package com.example.banksimulator.di

import android.content.Context
import androidx.room.Room
import com.example.banksimulator.data.local.BankDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideBankDatabase(
        @ApplicationContext context: Context
    ): BankDatabase {
        return Room.databaseBuilder(
            context,
            BankDatabase::class.java,
            "bank_database"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }
}