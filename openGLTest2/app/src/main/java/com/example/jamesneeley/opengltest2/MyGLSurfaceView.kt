package com.example.jamesneeley.opengltest2

import android.content.Context
import android.graphics.PointF
import android.opengl.GLSurfaceView
import android.view.MotionEvent
import java.util.*
import kotlin.concurrent.schedule

class MyGLSurfaceView(context: Context): GLSurfaceView(context) {

    private val _renderer: MyGLRenderer
    private val timer: TimerTask



    init {
        setEGLContextClientVersion(3)
        _renderer = MyGLRenderer()
        setRenderer(_renderer)

        // Render the view only when there is a change in the drawing data
        renderMode = RENDERMODE_WHEN_DIRTY


        timer = Timer("SettingUp", false).schedule(0, 200) {
            requestRender()
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> {

                _renderer.setTrianglePosition(context, PointF(event.x, event.y))
                requestRender()

            }
        }
        return true
    }
}