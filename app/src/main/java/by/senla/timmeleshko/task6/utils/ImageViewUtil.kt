package by.senla.timmeleshko.task6.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.submitImage(url: String, placeholder: Int) {
    Picasso.get()
        .load(url)
        .placeholder(placeholder)
        .into(this)
}