package com.example.a08_saveimagetoroom.ui.main

import android.app.Application
import com.example.a08_saveimagetoroom.data.local.UserDb
import com.example.a08_saveimagetoroom.data.repository.UserRepo

class UserApplication : Application() {
    val userDb by lazy { UserDb.getDatabase(this) }
    val userRepo by lazy { UserRepo(userDb.userDao()) }
}