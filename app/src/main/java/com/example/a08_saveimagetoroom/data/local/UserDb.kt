package com.example.a08_saveimagetoroom.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a08_saveimagetoroom.data.local.dao.UserDao
import com.example.a08_saveimagetoroom.data.local.entity.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDb : RoomDatabase(){
    abstract fun userDao(): UserDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: UserDb? = null

        fun getDatabase(context: Context): UserDb {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDb::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}