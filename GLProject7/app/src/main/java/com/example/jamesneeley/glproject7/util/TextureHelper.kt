package com.example.jamesneeley.glproject7.util

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES30
import android.opengl.GLUtils
import android.util.Log

object TextureHelper {

    fun loadTexture(context: Context, resourceID: Int): Int {
        val textureObjectID = IntArray(1)

        GLES30.glGenTextures(1, textureObjectID, 0) // generate only 1 texture with handle of textureObjectID and offset of 0

        if (textureObjectID[0] == 0) { //check if handle was created correctly
            if (LoggerConfig.ON) {
                Log.w(LoggerConfig.TAG, "Failed to generate Texture")
            }
            return 0
        }


        val options = BitmapFactory.Options()
        options.inScaled = false
        val bitmap = BitmapFactory.decodeResource(context.resources, resourceID, options)

        if (bitmap == null) {
            GLES30.glDeleteTextures(1, textureObjectID, 0)
            if (LoggerConfig.ON) {
                Log.w(LoggerConfig.TAG, "Failed to decode bitmap for texture creation")
            }
            return 0
        } //create bitmap from resourceID


        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureObjectID[0]) // bind to the texture so we can work with it. set type of texture 2D
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR_MIPMAP_LINEAR) //tell openGL to use trilinear filtering for Minification with the bound texture
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR) //tell openGL to use bilinear filtering for Magnification with the bound texture

        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0) // tells open GL to read in the bitmap and copy it over into the texture object that is currently bound.
        bitmap.recycle() //recycle the bitmap now that its been copied over to the texture. we dont need it in memory anymore.
        GLES30.glGenerateMipmap(GLES30.GL_TEXTURE_2D) //tells open GL to generate mipmap for the currently bound texture

        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0) //unbinds from the current texture so that we dont make changes to this texture in the future

        return textureObjectID[0] // return handle for the texture
    }
}