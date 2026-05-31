package com.rgi.amhisevikari.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.rgi.amhisevikari.data.local.AppDatabase
import com.rgi.amhisevikari.data.local.dao.BhajanDao
import com.rgi.amhisevikari.data.local.dao.CategoryDao
import com.rgi.amhisevikari.data.repository.BhajanRepositoryImpl
import com.rgi.amhisevikari.domain.repository.BhajanRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "amhi_sevekari_db"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(db: AppDatabase): CategoryDao {
        return db.categoryDao()
    }

    @Provides
    @Singleton
    fun provideBhajanDao(db: AppDatabase): BhajanDao {
        return db.bhajanDao()
    }

    @Provides
    @Singleton
    fun provideBhajanRepository(
        categoryDao: CategoryDao,
        bhajanDao: BhajanDao,
        firestore: FirebaseFirestore
    ): BhajanRepository {
        return BhajanRepositoryImpl(categoryDao, bhajanDao, firestore)
    }
}
