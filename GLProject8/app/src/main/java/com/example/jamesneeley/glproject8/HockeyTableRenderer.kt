package com.example.jamesneeley.glproject8

import android.content.Context
import android.graphics.Point
import android.graphics.PointF
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import com.example.jamesneeley.glproject8.programs.ColorShaderProgram
import com.example.jamesneeley.glproject8.programs.TextureShaderProgram
import com.example.jamesneeley.glproject8.objects.Mallet
import com.example.jamesneeley.glproject8.objects.Table
import com.example.jamesneeley.glproject8.util.TextureHelper

class HockeyTableRenderer(context: Context) : GLSurfaceView.Renderer {

    private val _context = context
    private val projectionMatrix = FloatArray(16)
    private val modelMatrix = FloatArray(16)
    private lateinit var table: Table
    private lateinit var mallet: Mallet
    private lateinit var textureProgram: TextureShaderProgram
    private lateinit var colorProgram: ColorShaderProgram
    private var texture: Int = 0


    private var malletPressed = false
    private var blueMalletPosition = PointF(0f, 0f)

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        table = Table()
        mallet = Mallet()
        textureProgram = TextureShaderProgram(_context)
        colorProgram = ColorShaderProgram(_context)
        texture = TextureHelper.loadTexture(_context, R.drawable.air_hockey_surface)
        blueMalletPosition = PointF(60 / 2 / 2f, 0.4f)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)

        perspectiveM(projectionMatrix, 0, 45f, width.toFloat()/ height.toFloat(), 1f, 10f)

        setIdentityM(modelMatrix, 0)
        translateM(modelMatrix,0, 0f, 0f, -2.5f)
        rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f)

        val temp = FloatArray(16)
        multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0)
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.count())
    }

    override fun onDrawFrame(gl: GL10?) {

        // Clear the rendering surface.
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)

        // Draw the table.
        textureProgram.useProgram()
        textureProgram.setUniforms(projectionMatrix, texture)
        table.bindData(textureProgram)
        table.draw()

        // Draw the mallets.
        colorProgram.useProgram()
        colorProgram.setUniforms(projectionMatrix)
        mallet.bindData(colorProgram)
        mallet.draw()
    }


    fun handleTouchPress(x: Float, y: Float) {
        val ray = convertNormalized2DPointToRay(x, y)

        val malletBoundingSphere = Sphere(blueMalletPosition.x, blueMalletPosition.y, blueMalletPosition.z, mallet.height/2f)
        malletPressed = Geometry.intersects(malletBoundingSphere, ray)


    }

    fun handleTouchDrag(x: Float, y: Float) {

    }
}