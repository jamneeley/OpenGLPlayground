package com.example.jamesneeley.glproject35

import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var _GLsurfaceView: MyGLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _GLsurfaceView = MyGLSurfaceView(this)
        setContentView(_GLsurfaceView)
    }

    override fun onResume() {
        super.onResume()
        _GLsurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        _GLsurfaceView.onPause()
    }
}
