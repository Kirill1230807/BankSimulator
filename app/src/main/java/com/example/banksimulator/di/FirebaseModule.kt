package com.example.banksimulator.di

import com.example.banksimulator.BuildConfig
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        val auth = FirebaseAuth.getInstance()

        if (BuildConfig.DEBUG) {
            try {
                auth.useEmulator("10.0.2.2", 9099)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return auth
    }
}