package com.example.jamesneeley.glproject7.programs

import android.content.Context
import android.opengl.GLES30
import com.example.jamesneeley.glproject7.R

class TextureShaderProgram(context: Context) :
    ShaderProgram(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader) {

    // Uniform locations
    private var uMatrixLocation: Int = 0
    private var uTextureUnitLocation: Int = 0
    // Attribute locations
    private var aPositionLocation: Int = 0
    private var aTextureCoordinatesLocation: Int = 0

    init {
        uMatrixLocation = GLES30.glGetUniformLocation(program, U_MATRIX)
        uTextureUnitLocation = GLES30.glGetUniformLocation(program, U_TEXTURE_UNIT)

        aPositionLocation = GLES30.glGetAttribLocation(program, A_POSITION)
        aTextureCoordinatesLocation = GLES30.glGetAttribLocation(program, A_TEXTURE_COORDINATES)
    }

    fun getPositionAttributeLocation(): Int {
        return aPositionLocation
    }

    fun getTextureCoordinatesAttributeLocation(): Int {
        return aTextureCoordinatesLocation
    }


    fun setUniforms(matrix: FloatArray, textureID: Int) {

        // Pass the matrix into the shader program.
        GLES30.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)

        // Set the active texture unit to texture unit 0.
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)

        // Bind the texture to this unit.
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureID)

        // Tell the texture uniform sampler to use this texture in the shader by
        // telling it to read from texture unit 0.
        GLES30.glUniform1i(uTextureUnitLocation, 0)
    }
}