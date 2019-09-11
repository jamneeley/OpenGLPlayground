package com.example.jamesneeley.glproject3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var mySurfaceView: MyGLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mySurfaceView = MyGLSurfaceView(this)
        setContentView(mySurfaceView)
    }

    override fun onResume() {
        super.onResume()
        mySurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mySurfaceView.onPause()
    }
}
