package com.company.art.utils

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.submitImage(url: String, placeholder: Drawable) {
    Picasso.get()
        .load(url)
        .placeholder(placeholder)
        .into(this);
}