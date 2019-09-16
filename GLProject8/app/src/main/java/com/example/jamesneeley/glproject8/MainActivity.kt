package com.example.jamesneeley.glproject8

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.util.Log
import com.example.jamesneeley.glproject8.util.LoggerConfig


class MainActivity : AppCompatActivity(), View.OnTouchListener {

    private lateinit var _glSurfaceView: MyGLSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _glSurfaceView = MyGLSurfaceView(this)
        _glSurfaceView.setOnTouchListener(this)
        setContentView(_glSurfaceView)
    }

    override fun onPause() {
        super.onPause()
        _glSurfaceView.onPause()
    }

    override fun onResume() {
        super.onResume()
        _glSurfaceView.onResume()
    }


    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        if (event != null && view == _glSurfaceView) {
            val normalizedX = event.x / view.width.toFloat() * 2 - 1
            val normalizedY = -(event.y / view.height.toFloat() * 2 - 1)

            Log.i(LoggerConfig.TAG, "X:$normalizedX\nY:$normalizedY\n   ")

            if (event.action == MotionEvent.ACTION_DOWN) {
                val runnable = {
                    run {
                        _glSurfaceView._renderer.handleTouchPress(
                            normalizedX, normalizedY)
                    }
                }
                _glSurfaceView.queueEvent(runnable)

            } else if (event.action == MotionEvent.ACTION_MOVE) {
                val runnable = {
                    run {
                        _glSurfaceView._renderer.handleTouchDrag(
                            normalizedX, normalizedY)
                    }
                }
                _glSurfaceView.queueEvent(runnable)
            }
            return true

        }
        return false
    }

}
