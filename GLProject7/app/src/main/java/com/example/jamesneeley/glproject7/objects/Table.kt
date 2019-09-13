package com.example.jamesneeley.glproject7.objects

import android.provider.SyncStateContract
import com.example.jamesneeley.glproject7.data.VertexArray
import com.example.jamesneeley.glproject7.util.Constants

class Table {

    private val POSITION_COMPONENT_COUNT = 2
    private val TEXTURE_COORDINATE_COMPONENT_COUNT = 2
    private val STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATE_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT
    private val vertexArray: VertexArray

    private val vertexData = floatArrayOf(
        // Order of coordinates: X, Y, S, T
        // Triangle Fan
        0f,    0f,    0.5f, 0.5f,
        -0.5f, -0.8f, 0f,   0.9f,
        0.5f,  -0.8f, 1f,   0.9f,
        0.5f,  0.8f,  1f,   0.1f,
        -0.5f, 0.8f,  0f,   0.1f,
        -0.5f, -0.8f, 0f,   0.9f
    )


    init {
        vertexArray = VertexArray(vertexData)
    }


    fun bindData(textureProgram: TextureShaderProgram) {

    }
}