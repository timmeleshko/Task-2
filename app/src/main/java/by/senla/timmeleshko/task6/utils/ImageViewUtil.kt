package by.senla.timmeleshko.task6.utils

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.submitImage(url: String, placeholder: Drawable) {
    Picasso.get()
        .load(url)
        .placeholder(placeholder)
        .into(this)
}