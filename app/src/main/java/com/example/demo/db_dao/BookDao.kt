package com.example.demo.db_dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.demo.model.Book

/**

@author dengfeijie
@description:
@date : 2020/7/7 17:35
 */
@Dao
interface BookDao {
    @Insert
    fun insertBook(book: Book): Long

    @Query("select * from Book")
    fun loadAllBooks(): List<Book>
}