package com.example.jamesneeley.glproject5

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix.orthoM
import com.example.jamesneeley.glproject5.util.ShaderHelper
import com.example.jamesneeley.glproject5.util.TextResourceReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import android.opengl.Matrix.orthoM



class HockeyTableRenderer(context: Context) : GLSurfaceView.Renderer {

    private val _context = context

    private val BYTES_PER_FLOAT = 4

    private val POSITION_COMPONENT_COUNT = 2
    private val COLOR_COMPONENT_COUNT = 3

    private val stride = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT

    private val A_COLOR = "a_Color"
    private val A_POSITION = "a_Position"
    private val U_MATRIX = "u_Matrix"

    private var aColorLocation = 0
    private var aPositionLocation = 0
    private var uMatrixLocation = 0
    private var program: Int = 0


    private val projectionMatrix = FloatArray(16)
    private val vertexData: FloatBuffer
    private val tableVertices = floatArrayOf(
// Order of coordinates: X, Y, R, G, B
// Triangle Fan
        0f, 0f, 1f, 1f, 1f,
        -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
        0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
        0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
        -0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
        -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
// Line 1
        -0.5f, 0f, 1f, 0f, 0f,
        0.5f, 0f, 1f, 0f, 0f,
// Mallets
        0f, -0.4f, 0f, 0f, 1f,
        0f, 0.4f, 1f, 0f, 0f
    )

    init {
        vertexData = ByteBuffer.allocateDirect(tableVertices.count() * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        vertexData.put(tableVertices)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)

        val vertexCode = TextResourceReader.readResourceFromID(_context, R.raw.simple_vertex_shader)
        val fragmentCode = TextResourceReader.readResourceFromID(_context, R.raw.simple_fragment_shader)

        val vertexHandle = ShaderHelper.compileVertexShader(vertexCode)
        val fragmentHandle = ShaderHelper.compileFragmentShader(fragmentCode)

        program = ShaderHelper.linkProgram(vertexHandle, fragmentHandle)
        ShaderHelper.validateProgram(program)

        GLES30.glUseProgram(program)

        aPositionLocation = GLES30.glGetAttribLocation(program, A_POSITION)
        aColorLocation = GLES30.glGetAttribLocation(program, A_COLOR)
        uMatrixLocation = GLES30.glGetUniformLocation(program, U_MATRIX)

        vertexData.position(0)
        GLES30.glVertexAttribPointer(
            aPositionLocation,
            POSITION_COMPONENT_COUNT,
            GLES30.GL_FLOAT,
            false,
            stride,
            vertexData
        )

        GLES30.glEnableVertexAttribArray(aPositionLocation)

        vertexData.position(POSITION_COMPONENT_COUNT)
        GLES30.glVertexAttribPointer(
            aColorLocation,
            COLOR_COMPONENT_COUNT,
            GLES30.GL_FLOAT,
            false,
            stride,
            vertexData
        )
        GLES30.glEnableVertexAttribArray(aColorLocation)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)

        val widthF = width.toFloat()
        val heightF = height.toFloat()

        val aspectRatio = if (widthF > heightF) {
            widthF / heightF
        } else {
            heightF / widthF
        }


        if (widthF > height) {
            // Landscape
            orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f)
        } else {
            // Portrait or square
            orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f)
        }
    }


    override fun onDrawFrame(gl: GL10?) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)

        GLES30.glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0)

        drawTable()
        drawDividingLine()
        drawPaddles()
    }

    private fun drawTable() {
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, 6)
    }

    private fun drawDividingLine() {
        GLES30.glDrawArrays(GLES30.GL_LINES, 6, 2)
    }

    private fun drawPaddles() {
        GLES30.glDrawArrays(GLES30.GL_POINTS, 8, 1)
        GLES30.glDrawArrays(GLES30.GL_POINTS, 9, 1)
    }
}