package com.example.jamesneeley.opengltest2

enum class TriangleType {
    TRIANGLE_ONE,
    TRIANGLE_TWO,
    TRIANGLE_THREE
}

/** Offset of the position data.  */
const val mPositionOffset = 0

/** Size of the position data in elements.  */
const val mPositionDataSize = 3

/** Offset of the color data.  */
const val mColorOffset = 3

/** Size of the color data in elements.  */
const val mColorDataSize = 4

/** How many elements per vertex.  */
const val bytesPerFloat = 4
const val mStrideBytes = 7 * bytesPerFloat