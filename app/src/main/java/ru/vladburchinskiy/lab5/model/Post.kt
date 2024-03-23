package ru.vladburchinskiy.lab5.model

data class Post(
    val id: Int = 0,
    val name: String,
    val message: String,
    val image: Int? = null
)
