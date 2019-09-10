package com.example.jamesneeley.glproject2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var _glSurfaceView: GLHockeySurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _glSurfaceView = GLHockeySurfaceView(this)
        setContentView(_glSurfaceView)
    }

    override fun onResume() {
        super.onResume()
        _glSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        _glSurfaceView.onPause()
    }
}
