package by.senla.timmeleshko.task6.model.beans

import java.io.Serializable

class Counter (
    val selections: Int? = null,
    val comments: Int? = null,
    val likes: Int? = null,
    val audioguides: Int? = null
) : Serializable