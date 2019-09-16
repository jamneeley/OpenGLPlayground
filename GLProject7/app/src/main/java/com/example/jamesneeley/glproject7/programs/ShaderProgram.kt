package com.example.jamesneeley.glproject7.programs

import android.content.Context
import android.opengl.GLES30
import com.example.jamesneeley.glproject7.util.ShaderHelper
import com.example.jamesneeley.glproject7.util.TextResourceReader

open class ShaderProgram(context: Context, vertexShaderResourceID: Int, fragmentShaderResourceID: Int ) {

    //uniform constants
    val U_MATRIX = "u_Matrix"
    val U_TEXTURE_UNIT = "u_TextureUnit"


//    attribute constants
    val A_POSITION = "a_Position"
    val A_COLOR = "a_Color"
    val A_TEXTURE_COORDINATES = "a_TextureCoordinates"

    var program = 0


    init {
        program = ShaderHelper.buildProgram(TextResourceReader.readResourceFromID(context, vertexShaderResourceID), TextResourceReader.readResourceFromID(context, fragmentShaderResourceID))
    }


    fun useProgram() {
        GLES30.glUseProgram(program)
    }
}