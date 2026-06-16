package com.example.banksimulator.di

import com.example.banksimulator.BuildConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        val firestore = FirebaseFirestore.getInstance()

        if (BuildConfig.DEBUG) {
            try {
                firestore.useEmulator("10.0.2.2", 8088)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return firestore
    }
}