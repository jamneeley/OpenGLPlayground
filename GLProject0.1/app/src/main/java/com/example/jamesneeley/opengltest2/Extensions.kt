package com.example.jamesneeley.opengltest2

import android.content.Context
import android.graphics.PointF

fun Context.convertPointToOpenGLCoordinates(point: PointF): FloatArray {
    val displayMetrics = resources.displayMetrics
    val width = displayMetrics.widthPixels / 2
    val height = displayMetrics.heightPixels / 2

    val x = if (point.x < width) {
        /**
         * left quadrant
         */
        (point.x / width) - 1
    } else {
        /**
         * right quadrant
         */
        ((point.x - width)/width)
    }


    val y = if (point.y < height) {
        /**
         * top quadrant
         */
        -((point.y / height) - 1)

    } else {
        /**
         * bottom quadrant
         */
        -((point.y - height)/height)
    }

    return floatArrayOf(x, y)
}