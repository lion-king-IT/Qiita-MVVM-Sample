package com.reo.running.qiita_mvvm_sample

import android.content.Context
import androidx.room.Room
import com.reo.running.qiita_mvvm_sample.model.SushiDatabase
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
    fun provideSushiDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        SushiDatabase::class.java,
        "database"
    ).build()

    @Provides
    @Singleton
    fun provideSushiDao(db: SushiDatabase) = db.suhiDao()
}