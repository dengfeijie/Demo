package com.example.demo.db_dao

import androidx.room.*
import com.example.demo.model.User

/**

@author dengfeijie
@description:
@date : 2020/7/6 16:57
 */


@Dao
interface UserDao {

//    @Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(newUser: User): Long

    @Update
    fun updateUser(newUser: User)

    @Query("select * from User")
    fun loadAllUsers(): List<User>


    @Query("select * from User where age > :age")
    fun loadUserOlderThan(age: Int): List<User>

    @Delete
    fun deleteUser(user: User)


    @Query("delete from User where lastName = :name")
    fun deleteUserByName(name: String): Int
}