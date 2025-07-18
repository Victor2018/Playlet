package com.victor.lib.coremodel.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
//import androidx.work.OneTimeWorkRequestBuilder
//import androidx.work.WorkManager

import com.victor.lib.coremodel.db.DbConfig.DATABASE_NAME
import com.victor.lib.coremodel.db.DbConfig.DB_VERSION
import com.victor.lib.coremodel.db.converters.DateConverters
import com.victor.lib.coremodel.db.dao.SearchKeywordDao
import com.victor.lib.coremodel.db.entity.SearchKeywordEntity
//import com.hok.lib.coremodel.workers.SeedDatabaseWorker

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AppDatabase
 * Author: Victor
 * Date: 2021/5/7 19:58
 * Description: 
 * -----------------------------------------------------------------
 */


@Database(entities = arrayOf(
        SearchKeywordEntity::class),
        version = DB_VERSION, exportSchema = false)
@TypeConverters(DateConverters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun searchKeywordDao(): SearchKeywordDao

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
//                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
//                            WorkManager.getInstance(context).enqueue(request)
                        }
                    })
                .fallbackToDestructiveMigration()//数据库更新时删除数据重新创建
                .build()
        }
    }
}