package com.example.jamesneeley.glproject7.programs

import android.content.Context
import android.opengl.GLES20.glUniformMatrix4fv
import android.opengl.GLES30
import com.example.jamesneeley.glproject7.R

class ColorShaderProgram(context: Context): ShaderProgram(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader) {

    // Uniform locations
    private var uMatrixLocation: Int = 0

    // Attribute locations
    private var aPositionLocation: Int = 0
    private var aColorLocation: Int = 0


    init {
        uMatrixLocation = GLES30.glGetUniformLocation(program, U_MATRIX)
        aPositionLocation = GLES30.glGetAttribLocation(program, A_POSITION)
        aColorLocation = GLES30.glGetAttribLocation(program, A_COLOR)
    }

    fun getPositionAttributeLocation(): Int {
        return aPositionLocation
    }

    fun getColorAttributeLocation(): Int {
        return aColorLocation
    }

    fun setUniforms(matrix: FloatArray) {
        // Pass the matrix into the shader program.
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
    }
}