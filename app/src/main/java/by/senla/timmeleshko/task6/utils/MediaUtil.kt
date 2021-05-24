package com.company.art.utils

import by.senla.timmeleshko.task6.model.beans.MediaDto

val sizes =
    arrayOf(0, 10, 20, 40, 80, 100, 200, 400, 800, 1000, 1200, 1400, 1800, 2000, 2200, 2400, 2800)

enum class MediaRatio {
    o,  //-соотношение сторон оригинального изображения
    s,  //-соотношение 1:1 (квадрат)
    h,  //-соотношение 3:2 (ландшафт)
    v   //-соотношение 3:5 (портрет)
}

enum class MediaSide {
    x, // по ширине
    y  // по высоте
}

fun buildMediaUrl(size: Float, media: MediaDto, ratio: MediaRatio, side: MediaSide): String {
    return media.let {
        val sizeImage = getCorrectSizeImage(size)
        val version = if (sizeImage > 1500) it.data?.version_big else it.data?.version
        "${it.base_url}/img/${ratio}${side}${sizeImage}/${it.use_type}/${version}/${it.media_id}.${it.data?.ext}"
    }
}

private fun getCorrectSizeImage(sizeImage: Float): Int {
    for (i in 0..sizes.size - 1) {
        if (i < sizes.size - 1 && sizeImage >= sizes[i] && sizeImage <= sizes[i + 1]) {
            return sizes[i]
        }
    }
    return sizes.size - 1
}