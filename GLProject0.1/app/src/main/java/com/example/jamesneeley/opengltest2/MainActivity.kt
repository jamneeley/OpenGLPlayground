package com.example.jamesneeley.opengltest2

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var _gLView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo
        val supportsEs3 = configurationInfo.reqGlEsVersion >= 0x30000

        if (supportsEs3) {
            Log.d("devTag", "does support openGL ES3")
        } else {
            Log.d("devTag", "does NOT support openGL ES3")
        }

        _gLView = MyGLSurfaceView(this)
        setContentView(_gLView)
    }

    override fun onPause() {
        super.onPause()
        _gLView.onPause()
    }

    override fun onResume() {
        super.onResume()
        _gLView.onResume()
    }
}
