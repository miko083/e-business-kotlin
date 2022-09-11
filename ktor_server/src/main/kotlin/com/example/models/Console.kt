package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Console(val id: String, val name: String, val category: Category)