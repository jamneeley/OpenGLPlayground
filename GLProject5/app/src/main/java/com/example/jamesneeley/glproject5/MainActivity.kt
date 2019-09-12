package com.example.jamesneeley.glproject5

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var _glSurfaceView: MyGLSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _glSurfaceView = MyGLSurfaceView(this)
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
