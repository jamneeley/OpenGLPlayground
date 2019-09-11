package com.example.jamesneeley.glproject3.util

import android.opengl.GLES30
import android.util.Log

object GLErrorHelper {

    private val TAG = "OpenGL"



    fun getErrors(methodTag: String) {
        do {
            val error = GLES30.glGetError()
            if (error == GLES30.GL_NO_ERROR) {
                break
            }

            if (LoggerConfig.ON) {
                Log.d(TAG, "ERROR: $error. caused at method: $methodTag")
            }

        } while (true)
    }
}