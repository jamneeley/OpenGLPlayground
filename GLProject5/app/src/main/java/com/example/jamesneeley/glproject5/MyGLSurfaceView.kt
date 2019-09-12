package com.example.jamesneeley.glproject5

import android.content.Context
import android.opengl.GLSurfaceView

class MyGLSurfaceView(context: Context): GLSurfaceView(context) {

    private val _renderer: GLSurfaceView.Renderer

    init {
        setEGLContextClientVersion(3)
        val renderer = HockeyTableRenderer(context)
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY
        _renderer = renderer
    }
}