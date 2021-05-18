package by.senla.timmeleshko.task3.model.beans

import java.io.Serializable
// используй неизменяемый тип переменных
data class Data(val count: Int? = null, var works: List<Work>? = null) : Serializable
