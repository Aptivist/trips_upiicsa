package com.aptivist.tripsupiicsaapp.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "photos")
data class PhotoEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "caption") val caption: String? = null,
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")val createdAt: Date? = null,
    @ColumnInfo(name = "modified_at", defaultValue = "CURRENT_TIMESTAMP") val modifiedAt: Date? = null
)
