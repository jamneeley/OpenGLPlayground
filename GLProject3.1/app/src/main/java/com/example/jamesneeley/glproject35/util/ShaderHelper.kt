package com.example.jamesneeley.glproject35.util

import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.opengl.GLES30
import android.util.Log
import javax.microedition.khronos.opengles.GL

object ShaderHelper {


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
                Log.d(LoggerConfig.TAG, "could not create new shader")
            }
            return 0
        }
        GLES30.glShaderSource(shaderObjectHandle, shaderCode) //upload shader source code into the shader via the shaderObjectHandle
        GLES30.glCompileShader(shaderObjectHandle)

        val compileStatus = IntArray(1)
        GLES30.glGetShaderiv(shaderObjectHandle, GLES30.GL_COMPILE_STATUS, compileStatus, 0)


        if (LoggerConfig.ON) {
            // Print the shader info log to the Android log output.
            Log.v(LoggerConfig.TAG, "Results of compiling source:" + "\n" + shaderCode + "\n:"
                    + GLES30.glGetShaderInfoLog(shaderObjectHandle))
        }

        if (compileStatus[0] == 0) {
            GLES30.glDeleteShader(shaderObjectHandle)
            if (LoggerConfig.ON) {
                Log.d(LoggerConfig.TAG, "Compilation of shader failed")
            }
            return 0
        }

        return shaderObjectHandle
    }

    fun linkProgram(vertexShaderID: Int, fragmentShaderID: Int): Int {
        val program = GLES30.glCreateProgram()

        if (program == 0) {
            if (LoggerConfig.ON) {
                Log.w(LoggerConfig.TAG, "Failed to create program")
            }
            return 0
        }
        GLES30.glAttachShader(program, vertexShaderID)
        GLES30.glAttachShader(program, fragmentShaderID)
        GLES30.glLinkProgram(program)

        val linkStatus = IntArray(1)
        GLES30.glGetProgramiv(program, GLES30.GL_LINK_STATUS, linkStatus, 0)
        if (linkStatus[0] == 0) {
            if (LoggerConfig.ON) {
                Log.w(LoggerConfig.TAG, "failed to link program ")
            }
            return 0
        }


        return program
    }

    fun validateProgram(programID: Int): Int {
        GLES30.glValidateProgram(programID)

        val validateStatus = IntArray(1)
        GLES30.glGetProgramiv(programID, GLES30.GL_VALIDATE_STATUS, validateStatus, 0)

        if (LoggerConfig.ON) {
            Log.v(LoggerConfig.TAG, "program validator status = ${validateStatus[0]}\n  Log: ${GLES30.glGetProgramInfoLog(programID)}")
        }

        return validateStatus[0]
    }
}