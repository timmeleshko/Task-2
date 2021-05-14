package by.senla.timmeleshko.task2.beans

import java.io.Serializable

class ListItem(var id: Int = 0, var title: String = "Title", var description: String = "Description") : Serializable {
    constructor(id: Int) : this(id, "Title", "Description")

    override fun toString(): String {
        return "NotesListItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description +
                '}';
    }
}