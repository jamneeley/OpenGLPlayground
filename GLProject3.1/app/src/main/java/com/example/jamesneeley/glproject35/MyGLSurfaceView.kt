package com.example.jamesneeley.glproject35

import android.content.Context
import android.opengl.GLSurfaceView
import android.opengl.GLSurfaceView.RENDERMODE_WHEN_DIRTY

class MyGLSurfaceView(context: Context): GLSurfaceView(context) {

    private val _renderer: HockeyTableRenderer

    init {
        setEGLContextClientVersion(3)
        val renderer = HockeyTableRenderer(context)
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY
        _renderer = renderer
    }
}