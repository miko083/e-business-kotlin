package com.example

import com.example.models.Category
import com.example.models.Console

// Define
val handheldCategory = Category("1", "Handheld")
val homeVideoCategory = Category("2", "Home Video Category")

val categoryStorage = mutableListOf<Category>(handheldCategory, homeVideoCategory)

val consoleStorage = mutableListOf<Console>(
    Console("1", "Playstation 3", homeVideoCategory),
    Console("2", "Xbox 360", homeVideoCategory),
    Console("3", "Playstation Portable", handheldCategory),
    Console("4", "Nintendo DS", handheldCategory)
)
