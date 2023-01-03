package com.jef4tech.interviewnotifier.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jef4tech.interviewnotifier.models.InterviewList


@Database(
    entities = [InterviewList::class],
    version = 1
)
abstract class InterViewDatabase : RoomDatabase(){
    abstract fun interviewDao(): InterviewDao

    companion object {
        @Volatile
        private var instance: InterViewDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                InterViewDatabase::class.java,
                "article_db.db"
            ).build()
    }
}