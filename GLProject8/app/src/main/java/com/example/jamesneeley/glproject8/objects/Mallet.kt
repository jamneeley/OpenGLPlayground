package com.example.jamesneeley.glproject8.objects

import android.opengl.GLES30
import com.example.jamesneeley.glproject8.data.VertexArray
import com.example.jamesneeley.glproject8.programs.ColorShaderProgram
import com.example.jamesneeley.glproject8.util.Constants

class Mallet {

    private val POSITION_COMPONENT_COUNT = 2
    private val COLOR_COMPONENT_COUNT = 3
    private val STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT


    private val vertexData = floatArrayOf(
        // Order of coordinates: X, Y, R, G, B
        0f, -0.4f, 0f, 0f, 1f,
        0f, 0.4f, 1f, 0f, 0f
    )

    private val vertexArray: VertexArray

    init {
        vertexArray = VertexArray(vertexData)
    }

    fun bindData(colorProgram: ColorShaderProgram) {

        vertexArray.setVertexAttributePointer(
            0,
            colorProgram.getPositionAttributeLocation(),
            POSITION_COMPONENT_COUNT,
            STRIDE
        )

        vertexArray.setVertexAttributePointer(
            POSITION_COMPONENT_COUNT,
            colorProgram.getColorAttributeLocation(),
            COLOR_COMPONENT_COUNT,
            STRIDE
        )

    }

    fun draw() {
        GLES30.glDrawArrays(GLES30.GL_POINTS, 0, 2)
    }
}