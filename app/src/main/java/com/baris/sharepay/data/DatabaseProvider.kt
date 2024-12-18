package com.baris.sharepay.data

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var database: SplitwiseDatabase? = null

    fun getDatabase(context: Context): SplitwiseDatabase {
        if (database == null) {
            synchronized(SplitwiseDatabase::class.java) {
                if (database == null) {
                    database = Room.databaseBuilder(
                        context.applicationContext,
                        SplitwiseDatabase::class.java,
                        "splitwise_db"
                    ).fallbackToDestructiveMigration().build()
                }
            }
        }
        return database!!
    }
}