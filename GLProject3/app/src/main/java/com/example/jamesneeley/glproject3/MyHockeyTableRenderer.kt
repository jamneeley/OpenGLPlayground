package com.example.jamesneeley.glproject3

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import com.example.jamesneeley.glproject3.util.ShaderHelper
import com.example.jamesneeley.glproject3.util.TextResourceReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyHockeyTableRenderer(context: Context) : GLSurfaceView.Renderer {

    private val _context = context

    private val U_COLOR = "u_Color"
    private val A_POSITION = "a_Position"
    private var uColorLocation: Int = 0
    private var aPositionLocation: Int = 0

    private val POSITION_COMPONENT_COUNT = 2
    private val BYTES_PER_FLOAT = 4

    private var program: Int = 0

    private val vertexData: FloatBuffer
    private val tableVertice = floatArrayOf(
// Triangle 1
        -0.5f, -0.5f,
        0.5f, 0.5f,
        -0.5f, 0.5f,
// Triangle 2
        -0.5f, -0.5f,
        0.5f, -0.5f,
        0.5f, 0.5f,
// Line 1
        -0.5f, 0f,
        0.5f, 0f,
// Mallets
        0f, -0.25f,

        0f, 0.25f
    )

    init {
        vertexData = ByteBuffer.allocateDirect(tableVertice.count() * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        vertexData.put(tableVertice).position(0)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        val vertexShaderSource = TextResourceReader.readTextFileFromResource(_context, R.raw.simple_vertex_shader)
        val fragmentShaderSource = TextResourceReader.readTextFileFromResource(_context, R.raw.simple_fragment_shader)

        val vertexShaderHandle: Int = ShaderHelper.compileVertexShader(vertexShaderSource)
        val fragmentShaderHandle: Int = ShaderHelper.compileFragmentShader(fragmentShaderSource)

        program = ShaderHelper.linkProgram(vertexShaderHandle, fragmentShaderHandle)
        ShaderHelper.validateProgram(program)
        GLES30.glUseProgram(program)

        uColorLocation = GLES30.glGetUniformLocation(program, U_COLOR)
        aPositionLocation = GLES30.glGetAttribLocation(program, A_POSITION)

        GLES30.glVertexAttribPointer(
            aPositionLocation,
            POSITION_COMPONENT_COUNT,
            GLES30.GL_FLOAT,
            false,
            0,
            vertexData
        ) // passing in wrong values can lead to funny results
        // and may even crash the program

        GLES30.glEnableVertexAttribArray(aPositionLocation)

    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)

        //table
        GLES30.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 6)

        //draw the dividing line
        GLES30.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        GLES30.glDrawArrays(GLES30.GL_LINES, 6, 2)

        // Draw the first mallet blue.
        GLES30.glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
        GLES30.glDrawArrays(GLES30.GL_POINTS, 8, 1)

        // Draw the second mallet red.
        GLES30.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        GLES30.glDrawArrays(GLES30.GL_POINTS, 9, 1)
    }
}