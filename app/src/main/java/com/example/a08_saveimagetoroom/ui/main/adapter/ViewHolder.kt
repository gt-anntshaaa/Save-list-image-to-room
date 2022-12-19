package com.example.a08_saveimagetoroom.ui.main.adapter

import com.example.a08_saveimagetoroom.data.local.entity.User

interface ViewHolder {
    fun bind(user: User)
    fun delete(user: User)
}