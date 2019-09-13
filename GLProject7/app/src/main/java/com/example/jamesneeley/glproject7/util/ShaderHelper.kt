package com.example.jamesneeley.glproject7.util

import android.opengl.GLES30
import android.util.Log
object ShaderHelper {

    fun compileVertexShader(shaderCode: String): Int {
        return compileShader(GLES30.GL_VERTEX_SHADER, shaderCode)
    }

    fun compileFragmentShader(shaderCode: String): Int {
        return compileShader(GLES30.GL_FRAGMENT_SHADER, shaderCode)
    }

    private fun compileShader(type: Int, shaderCode: String): Int {
        val shaderHandle = GLES30.glCreateShader(type)

        if (shaderHandle == 0 ) {
            if (LoggerConfig.ON) {
                Log.w(LoggerConfig.TAG, "Unable to create shader of type: $type")
            }
            return 0
        }

        GLES30.glShaderSource(shaderHandle, shaderCode)
        GLES30.glCompileShader(shaderHandle)

        val compileStatus = IntArray(1)
        GLES30.glGetShaderiv(shaderHandle, GLES30.GL_COMPILE_STATUS, compileStatus, 0)

        if (compileStatus[0] == 0) {
            GLES30.glDeleteShader(shaderHandle)
            if (LoggerConfig.ON) {
                Log.w(LoggerConfig.TAG, "Unable to compile shader")
            }
            return 0
        }

        return shaderHandle
    }

    fun linkProgram(vertexID: Int, fragmentID: Int): Int {
        val program = GLES30.glCreateProgram()

        if (program == 0) {
            if (LoggerConfig.ON) {
                Log.w(LoggerConfig.TAG, "Unable to create program")
            }
            return 0
        }

        GLES30.glAttachShader(program, vertexID)
        GLES30.glAttachShader(program, fragmentID)
        GLES30.glLinkProgram(program)

        val linkStatus = IntArray(1)
        GLES30.glGetProgramiv(program, GLES30.GL_LINK_STATUS, linkStatus, 0)
        if (linkStatus[0] == 0) {
            GLES30.glDeleteProgram(program)
            if (LoggerConfig.ON) {
                Log.w(LoggerConfig.TAG, "Unable to link program")
            }
            return 0
        }
        return program
    }


    fun validateProgram(program: Int): Int {

        GLES30.glValidateProgram(program)

        val validateStatus = IntArray(1)
        GLES30.glGetProgramiv(program, GLES30.GL_VALIDATE_STATUS, validateStatus, 0)

        if (validateStatus[0] == 0) {
            if (LoggerConfig.ON) {
                Log.w(LoggerConfig.TAG, "Program not valid: Status - ${validateStatus[0]}. Log: ${GLES30.glGetProgramInfoLog(program)}")
            }
        }

        return validateStatus[0]
    }
}