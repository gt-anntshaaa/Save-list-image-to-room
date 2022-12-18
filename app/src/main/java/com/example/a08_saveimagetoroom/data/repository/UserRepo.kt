package com.example.a08_saveimagetoroom.data.repository

import androidx.annotation.WorkerThread
import com.example.a08_saveimagetoroom.data.local.dao.UserDao
import com.example.a08_saveimagetoroom.data.local.entity.User
import kotlinx.coroutines.flow.Flow

class UserRepo(private val userDao: UserDao) {
    val allUser: Flow<List<User>> = userDao.getAllUser()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: User){
        userDao.insert(user)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(user: User){
        userDao.delete(user)
    }
}