package com.example.jamesneeley.opengltest2

import android.opengl.GLES30
import android.util.Log

open class GLShape {

    fun loadShader(type: Int, shaderCode: String): Int {
        val compiled = IntArray(1)

        // Load in the vertex shader.
        val shaderHandle = GLES30.glCreateShader(type)

        // Pass in the shader source.
        GLES30.glShaderSource(shaderHandle, shaderCode)
        // Compile the shader.
        GLES30.glCompileShader(shaderHandle)
        // Get the compilation status.
        GLES30.glGetShaderiv(shaderHandle, GLES30.GL_COMPILE_STATUS, compiled, 0)

        // If the compilation failed, delete the shader.
        if (compiled[0] == 0) {
            val infoLog = GLES30.glGetShaderInfoLog(shaderHandle)
            throw java.lang.RuntimeException("Compilation Failed: $infoLog")
        }
        return shaderHandle
    }

    fun checkGlError(glOperation: String) {
        val error: Int = GLES30.glGetError()
        if (error != GLES30.GL_NO_ERROR) {
            Log.e("devTag", "$glOperation: glError $error")
            throw RuntimeException("$glOperation: glError $error")
        }
    }
}