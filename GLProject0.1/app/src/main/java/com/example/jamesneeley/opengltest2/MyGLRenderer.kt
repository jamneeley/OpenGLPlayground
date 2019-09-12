package com.example.jamesneeley.opengltest2

import android.content.Context
import android.graphics.PointF
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class MyGLRenderer : GLSurfaceView.Renderer {

    /**
     * Store the view matrix. This can be thought of as our camera. This matrix transforms world space to eye space;
     * it positions things relative to our eye.
     */

    private val mViewMatrix = FloatArray(16)
    private val mProjectionMatrix = FloatArray(16)
    private val mModelMatrix = FloatArray(16)
    private val mMVPMatrix = FloatArray(16)

    private var currentType: TriangleType = TriangleType.TRIANGLE_ONE

    private lateinit var triangle1: Triangle
//    private lateinit var triangle2: Triangle
//    private lateinit var triangle3: Triangle

    fun setCurrentType(type: TriangleType) {
        currentType = type
    }

    fun setTrianglePosition(context: Context, triangleCenter: PointF) {

        triangle1.adjustTrianglePositionToTouch(context, triangleCenter)

//        when (currentType) {
//            TriangleType.TRIANGLE_ONE -> triangle1.adjustTrianglePositionToTouch(context, triangleCenter)
//            TriangleType.TRIANGLE_TWO -> triangle2.adjustTrianglePositionToTouch(context, triangleCenter)
//            TriangleType.TRIANGLE_THREE -> triangle3.adjustTrianglePositionToTouch(context, triangleCenter)
//        }
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        triangle1 = Triangle()
//        triangle2 = Triangle()
//        triangle3 = Triangle()
//        clear background color to white
        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 1.0f)

//        position tge eye behind the origin
        val eyeX = 0.0f
        val eyeY = 0.0f
        val eyeZ = 1.5f

//        we are looking toward the distance
        val lookX = 0.0f
        val lookY = 0.0f
        val lookZ = -5.0f

//        set up out vector. this is where our head would be pointing where we are holding the camera
        val upX = 0.0f
        val upY = 1.0f
        val upZ = 0.0f

        // Set the view matrix. This matrix can be said to represent the camera position.
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {

        //set the openGL Viewport to the same size as the surface
        GLES30.glViewport(0, 0, width, height)

        val ratio = (width.toFloat() / height.toFloat())

        val left = -ratio
        val right = ratio
        val bottom = -1.0f
        val top = 1.0f
        val near = 1.0f
        val far = 10.0f

//        Matrix.orthoM(mProjectionMatrix, 0, left, right, bottom, top, near, far)
        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT or GLES30.GL_COLOR_BUFFER_BIT)
//        val time = SystemClock.uptimeMillis() % 10000
//        val angleInDegrees = (360f / 10000f) * time
        Matrix.setIdentityM(mModelMatrix, 0)
//        Matrix.rotateM(mModelMatrix, 0, angleInDegrees, 0.0f, 0.0f, 1.0f)
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0)
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0)

        triangle1.draw(mMVPMatrix)

//        when (currentType) {
//            TriangleType.TRIANGLE_ONE -> {
//                triangle1.draw(mMVPMatrix)
//            }
//
//            TriangleType.TRIANGLE_TWO -> {
//                triangle1.draw(mMVPMatrix)
//                triangle1.draw(mMVPMatrix)
//            }
//
//            TriangleType.TRIANGLE_THREE -> {
//                triangle1.draw(mMVPMatrix)
//                triangle1.draw(mMVPMatrix)
//                triangle1.draw(mMVPMatrix)
//            }
//        }
    }
}