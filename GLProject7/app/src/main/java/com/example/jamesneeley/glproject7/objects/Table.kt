package com.example.jamesneeley.glproject7.objects

import android.opengl.GLES30
import android.provider.SyncStateContract
import com.example.jamesneeley.glproject7.data.VertexArray
import com.example.jamesneeley.glproject7.programs.TextureShaderProgram
import com.example.jamesneeley.glproject7.util.Constants

class Table {

    private val POSITION_COMPONENT_COUNT = 2
    private val TEXTURE_COORDINATE_COMPONENT_COUNT = 2
    private val STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATE_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT
    private val vertexArray: VertexArray

    private val vertexData = floatArrayOf(
        // Order of coordinates: X, Y, S, T
        // Triangle Fan
        0f, 0f, 0.5f, 0.5f,
        -0.5f, -0.8f, 0f, 0.9f,
        0.5f, -0.8f, 1f, 0.9f,
        0.5f, 0.8f, 1f, 0.1f,
        -0.5f, 0.8f, 0f, 0.1f,
        -0.5f, -0.8f, 0f, 0.9f
    )


    init {
        vertexArray = VertexArray(vertexData)
    }


    fun bindData(textureProgram: TextureShaderProgram) {
        vertexArray.setVertexAttributePointer(
            0,
            textureProgram.getPositionAttributeLocation(),
            POSITION_COMPONENT_COUNT,
            STRIDE
        )
        vertexArray.setVertexAttributePointer(
            POSITION_COMPONENT_COUNT,
            textureProgram.getTextureCoordinatesAttributeLocation(),
            TEXTURE_COORDINATE_COMPONENT_COUNT,
            STRIDE
        )
    }

    fun draw() {
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, 6)
    }
}