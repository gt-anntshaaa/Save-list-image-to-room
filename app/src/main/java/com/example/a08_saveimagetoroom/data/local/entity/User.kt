package com.example.a08_saveimagetoroom.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class User(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "path") val path: String
) : Parcelable {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
