package com.example.testingapp.models

data class Food(
    val type: String,
    val name: String? = null,
    val size: String? = null,
    val shape: String? = null,
    val grain: String? = null,
    val meat: String? = null,
    val length: String? = null,
    val ingredients: ArrayList<String>? = null,
    val toppings: ArrayList<String>? = null,
    val sauce: ArrayList<String>? = null,
    var price: Double
)

data class Order(
    val order: ArrayList<Food>
)

data class ExchangeRate(
    val USD: Double,
    val GBP: Double,
    val AUD: Double
)