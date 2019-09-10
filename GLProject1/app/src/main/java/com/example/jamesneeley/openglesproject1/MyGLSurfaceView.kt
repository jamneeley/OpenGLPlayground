package com.example.jamesneeley.openglesproject1

import android.content.Context
import android.opengl.GLSurfaceView

class MyGLSurfaceView(context: Context): GLSurfaceView(context) {


    private val _renderer: MyGLRenderer

    init {
        val renderer = MyGLRenderer()
        setEGLContextClientVersion(3)
        setRenderer(renderer)
        _renderer = renderer
    }
}