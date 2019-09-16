package com.example.jamesneeley.glproject8.data

import android.opengl.GLES30
import com.example.jamesneeley.glproject8.util.Constants.BYTES_PER_FLOAT
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class VertexArray(vertexData: FloatArray) {

    private val floatBuffer: FloatBuffer = ByteBuffer.allocateDirect(vertexData.count() * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertexData)

    fun setVertexAttributePointer(dataOffset: Int, attributeLocation: Int, componentCount: Int, stride: Int) {
        floatBuffer.position(dataOffset)
        GLES30.glVertexAttribPointer(attributeLocation, componentCount, GLES30.GL_FLOAT, false, stride, floatBuffer)
        GLES30.glEnableVertexAttribArray(attributeLocation)
        floatBuffer.position(0)
    }
}