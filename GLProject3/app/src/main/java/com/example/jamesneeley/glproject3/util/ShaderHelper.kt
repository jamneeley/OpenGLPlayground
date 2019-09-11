package com.example.jamesneeley.glproject3.util

import android.opengl.GLES30
import android.util.Log

object ShaderHelper {
    private val TAG = "ShaderHelper"


    fun compileVertexShader(shaderCode: String): Int {
        return compileShader(GLES30.GL_VERTEX_SHADER, shaderCode)
    }

    fun compileFragmentShader(shaderCode: String): Int {
        return compileShader(GLES30.GL_FRAGMENT_SHADER, shaderCode)
    }

    private fun compileShader(type: Int, shaderCode: String): Int {

        val shaderObjectHandle = GLES30.glCreateShader(type) //create shader

        if (shaderObjectHandle == 0) {
            if (LoggerConfig.ON) {
                Log.d(TAG, "could not create new shader")
            }
            return 0
        }
        GLES30.glShaderSource(shaderObjectHandle, shaderCode) //upload shader source code into the shader via the shaderObjectHandle
        GLES30.glCompileShader(shaderObjectHandle)

        val compileStatus = IntArray(1)
        GLES30.glGetShaderiv(shaderObjectHandle, GLES30.GL_COMPILE_STATUS, compileStatus, 0)


        if (LoggerConfig.ON) {
            // Print the shader info log to the Android log output.
            Log.v(TAG, "Results of compiling source:" + "\n" + shaderCode + "\n:"
                    + GLES30.glGetShaderInfoLog(shaderObjectHandle))
        }

        if (compileStatus[0] == 0) {
            GLES30.glDeleteShader(shaderObjectHandle)
            if (LoggerConfig.ON) {
                Log.d(TAG, "Compilation of shader failed")
            }
            return 0
        }

        return shaderObjectHandle
    }

    fun linkProgram(vertexShaderID: Int, fragmentShaderId: Int): Int {
        val programObjectID = GLES30.glCreateProgram()

        if (programObjectID == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create program")
            }
            return 0
        }

        GLES30.glAttachShader(programObjectID, vertexShaderID)
        GLES30.glAttachShader(programObjectID, fragmentShaderId)

        GLES30.glLinkProgram(programObjectID)

        val linkStatus = IntArray(1)

        GLES30.glGetProgramiv(programObjectID, GLES30.GL_LINK_STATUS, linkStatus,0)

        if (LoggerConfig.ON) {
            Log.v(TAG, "Results of Linking Program: \n ${GLES30.glGetProgramInfoLog(programObjectID)}")
        }


        if (linkStatus[0] == 0) {
            GLES30.glDeleteProgram(programObjectID)
            if (LoggerConfig.ON) {
                Log.w(TAG, "Linking of Program Failed")
            }
            return 0
        }

        return programObjectID
    }

    fun validateProgram(programHandle: Int): Int {
        GLES30.glValidateProgram(programHandle)

        val validateStatus = IntArray(1)
        GLES30.glGetProgramiv(programHandle, GLES30.GL_VALIDATE_STATUS, validateStatus, 0)

        if (LoggerConfig.ON) {
            Log.v(TAG, "Results of validating program: ${validateStatus[0]} \n Log: ${GLES30.glGetProgramInfoLog(programHandle)}")
        }

        return validateStatus[0]
    }
}