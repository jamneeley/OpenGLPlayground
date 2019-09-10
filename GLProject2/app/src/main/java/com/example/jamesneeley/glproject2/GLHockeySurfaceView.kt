package com.example.jamesneeley.glproject2

import android.content.Context
import android.opengl.GLSurfaceView

class GLHockeySurfaceView(context: Context): GLSurfaceView(context) {

    private val _renderer: AirHockeyRenderer

    init {
        setEGLContextClientVersion(3)
        val renderer = AirHockeyRenderer()
        setRenderer(renderer)

        renderMode = RENDERMODE_WHEN_DIRTY //needs to be set after setRenderer is called
        _renderer = renderer
    }
}