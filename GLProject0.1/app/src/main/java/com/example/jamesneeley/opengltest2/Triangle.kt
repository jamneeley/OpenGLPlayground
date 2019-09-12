package com.example.jamesneeley.opengltest2

import android.content.Context
import android.graphics.PointF
import android.opengl.GLES30
import java.lang.RuntimeException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class Triangle : GLShape() {

    private val triangleVerticesData = floatArrayOf(
        -0.75f, -0.75f, 0.0f,
        0.8f, 0.07f, 0.6f, 1.0f,

        0.75f, -0.75f, 0.0f,
        0.8f, 0.07f, 0.6f, 1.0f,

        0.0f, 0.75f, 0.0f,
        0.3f, 0.01f, 1.0f, 1.0f
    )


    val vertexShader = (
            "uniform mat4 u_MVPMatrix;" +     // A constant representing the combined model/view/projection matrix.
                    "attribute vec4 a_Position;" +    // Per-vertex position information we will pass in.
                    "attribute vec4 a_Color;" +   // Per-vertex color information we will pass in.
                    "varying vec4 v_Color;" +    // This will be passed into the fragment shader.
                    "void main()" +     // The entry point for our vertex shader.
                    "{" +
                    "v_Color = a_Color;" +    // Pass the color through to the fragment shader. It will be interpolated across the triangle.
                    "gl_Position = u_MVPMatrix * a_Position;" +   // gl_Position is a special variable used to store the final position. and Multiply the vertex by the matrix to get the final point in
                    "}")    // normalized screen coordinates.


    val fragmentShader = (
            "precision mediump float;" +   // Set the default precision to medium. We don't need as high of a precision in the fragment shader.
                    "varying vec4 v_Color;" +    // This is the color from the vertex shader interpolated across the triangle per fragment.
                    "void main()" +    // The entry point for our fragment shader.
                    "{" +
                    "gl_FragColor = v_Color;" +   // Pass the color directly through the pipeline.
                    "}")


    private var floatBuffer: FloatBuffer = FloatBuffer.allocate(0)

    private val mProgramHandle: Int
    private val mVertexShaderHandle: Int
    private val mFragmentShaderHandle: Int
    private var mMVPMatrixHandle: Int = 0
    private var mPositionHandle: Int = 0
    private var mColorHandle: Int = 0


    init {

        /**
         * create shaders
         * check for shader compilation success in GLShape
         * create program
         * bind shaders together in program
         * check for program compilation success
         * set instance variables for handles
         */

        val vertexShaderHandle: Int = loadShader(GLES30.GL_VERTEX_SHADER, vertexShader)
        val fragmentShaderHandle: Int = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentShader)

        // create empty OpenGL ES Program
        val programHandle = GLES30.glCreateProgram()

        // add the vertex shader to program
        GLES30.glAttachShader(programHandle, vertexShaderHandle)

        // add the fragment shader to program
        GLES30.glAttachShader(programHandle, fragmentShaderHandle)


        GLES30.glBindAttribLocation(programHandle, 0, "a_Position")
        GLES30.glBindAttribLocation(programHandle, 1, "a_Color")

        //links both shaders together in the program
        GLES30.glLinkProgram(programHandle)

        val linkStatus = intArrayOf(1)
        //check if program compiled correctly
        GLES30.glGetProgramiv(programHandle, GLES30.GL_LINK_STATUS, linkStatus, 0)
        if (linkStatus[0] == 0) {
            throw RuntimeException("Error Creating Program ${GLES30.glGetProgramInfoLog(programHandle)}")
        }

        mVertexShaderHandle = vertexShaderHandle
        mFragmentShaderHandle = fragmentShaderHandle
        mProgramHandle = programHandle

        initializeBuffers()
    }

    private fun initializeBuffers() {
        val buffer = ByteBuffer.allocateDirect(triangleVerticesData.count() * bytesPerFloat).run {
            order(ByteOrder.nativeOrder())
            asFloatBuffer()
        }
        buffer.put(triangleVerticesData).position(0)
        floatBuffer = buffer
    }

    fun adjustTrianglePositionToTouch(context: Context, position: PointF) {
        val outerRadius = 300.0f
        val apothem = Math.cos(30.0).toFloat() * outerRadius
        val halvedLength = Math.sin(30.0).toFloat() * outerRadius

        replaceTriaglePoints(
            context,
            PointF(
                position.x - halvedLength, position.y + apothem
            ),
            PointF(position.x + halvedLength, position.y + apothem),
            PointF(position.x, position.y - outerRadius)
        )
        initializeBuffers()
    }

    fun replaceTriaglePoints(context: Context, point1: PointF, point2: PointF, point3: PointF) {
        val verticeOne = context.convertPointToOpenGLCoordinates(point1)
        val verticeTwo = context.convertPointToOpenGLCoordinates(point2)
        val verticeThree = context.convertPointToOpenGLCoordinates(point3)

        triangleVerticesData[0] = verticeOne[0]
        triangleVerticesData[1] = verticeOne[1]

        triangleVerticesData[7] = verticeTwo[0]
        triangleVerticesData[8] = verticeTwo[1]

        triangleVerticesData[14] = verticeThree[0]
        triangleVerticesData[15] = verticeThree[1]
    }


    fun draw(mvpMatrix: FloatArray) {
        GLES30.glUseProgram(mProgramHandle)
        mMVPMatrixHandle = GLES30.glGetUniformLocation(mProgramHandle, "u_MVPMatrix")
        mPositionHandle = GLES30.glGetAttribLocation(mProgramHandle, "a_Position")
        mColorHandle = GLES30.glGetAttribLocation(mProgramHandle, "a_Color")

//        pass in vertece information
        floatBuffer.position(mPositionOffset)
        GLES30.glVertexAttribPointer(
            mPositionHandle,
            mPositionDataSize,
            GLES30.GL_FLOAT,
            false,
            mStrideBytes,
            floatBuffer
        )
        GLES30.glEnableVertexAttribArray(mPositionHandle)


        //pass in color information
        floatBuffer.position(mColorOffset)
        GLES30.glVertexAttribPointer(
            mColorHandle, mColorDataSize,
            GLES30.GL_FLOAT,
            false,
            mStrideBytes,
            floatBuffer
        )
        GLES30.glEnableVertexAttribArray(mColorHandle)


        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3)
    }
}