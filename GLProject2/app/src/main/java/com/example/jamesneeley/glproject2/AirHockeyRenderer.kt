package com.example.jamesneeley.glproject2

import android.opengl.GLES30
import android.opengl.GLSurfaceView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class AirHockeyRenderer: GLSurfaceView.Renderer {


    private val POSITION_COMPONENT_COUNT = 2
    private val BYTES_PER_FLOAT = 4

    private val vertexData : FloatBuffer

    private val tableVertices = floatArrayOf(
//        triangle 1
        0f, 0f,
        0f, 14f,
        0f, 14f,

        //triangle 2
        0f, 0f,
        9f, 0f,
        9f, 14f,

        //line
        0f, 7f,
        9f, 7f,

        //mallets
        4.5f, 2f,
        4.5f, 12f
    )

    init {
        vertexData = ByteBuffer.allocateDirect(tableVertices.count() * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer()
        vertexData.put(tableVertices).position(0)
    }


    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES30.glClearColor(0.0f, 1.0f, 0.0f, 1.0f)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
    }
}