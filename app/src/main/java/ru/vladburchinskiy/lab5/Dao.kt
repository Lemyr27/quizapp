package ru.vladburchinskiy.lab5

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.vladburchinskiy.lab5.model.Post

@Dao
interface Dao {
    @Insert
    fun addPost(post: Post)
    @Query("SELECT * FROM post")
    fun getAllPosts(): Flow<List<Post>>
}