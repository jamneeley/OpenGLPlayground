package com.example.jamesneeley.glproject3

import android.content.Context
import android.opengl.GLSurfaceView

class MyGLSurfaceView(context: Context): GLSurfaceView(context) {

    private val _renderer: MyHockeyTableRenderer

    init {
        setEGLContextClientVersion(2)
        val renderer = MyHockeyTableRenderer(context)
        setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY
        _renderer = renderer
    }
}