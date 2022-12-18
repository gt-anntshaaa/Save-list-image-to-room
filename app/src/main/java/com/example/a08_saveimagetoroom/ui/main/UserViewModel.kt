package com.example.a08_saveimagetoroom.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.a08_saveimagetoroom.data.local.entity.User
import com.example.a08_saveimagetoroom.data.repository.UserRepo
import kotlinx.coroutines.launch

class UserViewModel(private val userRepo: UserRepo) : ViewModel() {
    val allUser: LiveData<List<User>> = userRepo.allUser.asLiveData()

    fun insert(user: User) = viewModelScope.launch { userRepo.insert(user) }

    fun delete(user: User) = viewModelScope.launch { userRepo.delete(user) }
}