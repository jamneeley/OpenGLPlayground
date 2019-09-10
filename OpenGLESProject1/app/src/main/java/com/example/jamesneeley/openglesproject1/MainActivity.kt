package com.example.jamesneeley.openglesproject1

import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var _myGLSurfaceView: MyGLSurfaceView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _myGLSurfaceView = MyGLSurfaceView(this)
        _myGLSurfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
        setContentView(_myGLSurfaceView)
    }

    override fun onResume() {
        super.onResume()
        _myGLSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        _myGLSurfaceView.onPause()
    }
}

