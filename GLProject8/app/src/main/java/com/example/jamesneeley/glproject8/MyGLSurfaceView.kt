package com.example.jamesneeley.glproject8

import android.content.Context
import android.opengl.GLSurfaceView

class MyGLSurfaceView(context: Context): GLSurfaceView(context) {


    val _renderer: HockeyTableRenderer

    init {
        setEGLContextClientVersion(3)
        val renderer = HockeyTableRenderer(context)
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY
        _renderer = renderer
    }
}