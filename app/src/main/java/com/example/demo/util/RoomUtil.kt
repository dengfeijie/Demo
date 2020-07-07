package com.example.demo.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.demo.db_dao.BookDao
import com.example.demo.db_dao.UserDao
import com.example.demo.model.Book
import com.example.demo.model.User

/**

@author dengfeijie
@description:
@date : 2020/7/6 17:10
 */

@Database(version = 2, entities = [User::class, Book::class])
abstract class RoomUtil : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao


    companion object {
        private var instance: RoomUtil? = null


        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("create table Book (id integer primary key autoincrement not null, name text not null, pages integer not null)")
            }

        }


        @Synchronized
        fun getDatabase(context: Context): RoomUtil? {

            //instance不为空时才会执行let代码
            instance?.let {
                return instance
            }

            return Room.databaseBuilder(context.applicationContext,
                RoomUtil::class.java,
                "app_database")
                .addMigrations(MIGRATION_1_2)
                .build().apply {
                    instance = this
                }
        }

    }

}