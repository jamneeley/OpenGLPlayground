package com.example.jamesneeley.glproject35

import android.content.Context
import android.graphics.Shader
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import com.example.jamesneeley.glproject35.util.ShaderHelper
import com.example.jamesneeley.glproject35.util.TextResourceReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL
import javax.microedition.khronos.opengles.GL10

class HockeyTableRenderer(context: Context) : GLSurfaceView.Renderer {

    private val _context = context


    private val U_COLOR = "u_Color"
    private val A_POSITION = "a_Position"
    private var uColorLocation = 0
    private var aPositionLocation = 0


    private val POSITION_COMPONENT_COUNT = 2


    private var program = 0
    private val BYTES_PER_FLOAT = 4


    private val vertexData: FloatBuffer
    private val tableVertices = floatArrayOf(
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
        vertexData = ByteBuffer.allocateDirect(tableVertices.count() * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        vertexData.put(tableVertices).position(0)
    }


    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        val vertexCode = TextResourceReader.readTextFromResource(_context, R.raw.simple_vertex_shader)
        val fragmentCode = TextResourceReader.readTextFromResource(_context, R.raw.simple_fragment_shader)


        val vertexHandle = ShaderHelper.compileVertexShader(vertexCode)
        val fragmentHandle = ShaderHelper.compileFragmentShader(fragmentCode)

        program = ShaderHelper.linkProgram(vertexHandle, fragmentHandle)
        ShaderHelper.validateProgram(program)

        GLES30.glUseProgram(program)

        aPositionLocation = GLES30.glGetAttribLocation(program, A_POSITION)
        uColorLocation = GLES30.glGetUniformLocation(program, U_COLOR)

        GLES30.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES30.GL_FLOAT, false, 0, vertexData)
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

        GLES30.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        GLES30.glDrawArrays(GLES30.GL_LINES, 6, 2)

        GLES30.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        GLES30.glDrawArrays(GLES30.GL_POINTS, 8, 1)

        GLES30.glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
        GLES30.glDrawArrays(GLES30.GL_POINTS, 9, 1)
    }
}