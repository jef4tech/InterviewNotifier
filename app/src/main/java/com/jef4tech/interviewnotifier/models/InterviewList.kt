package com.jef4tech.interviewnotifier.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "interviewList"
)
data class InterviewList(
    @ColumnInfo(name = "companyname")
    var companyName : String,
    @ColumnInfo(name = "location")
    var location : String,
    @ColumnInfo(name = "type")
    var jobType : String,
    @ColumnInfo(name = "salaryrange")
    var salaryRange : String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long?,

    )