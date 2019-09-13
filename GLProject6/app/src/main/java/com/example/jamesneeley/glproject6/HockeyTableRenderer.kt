package com.example.jamesneeley.glproject6

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix.*
import com.example.jamesneeley.glproject6.util.ShaderHelper
import com.example.jamesneeley.glproject6.util.TextResourceReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class HockeyTableRenderer(context: Context) : GLSurfaceView.Renderer {


    private val _context = context

    private val BYTES_PER_FLOAT =
        4    //when allocating native memory we need to know how big the memory block is and each float holds 4 bytes or 32 bits

    private val POSITION_COMPONENT_COUNT =
        2 //used to calculate the stride, position of color component offset, and the length of position components
    private val COLOR_COMPONENT_COUNT = 3 //used to calculate stride and length of color components

    private val stride =
        (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT //open gl used this to move the offset down the vertexData to get the next position or color

    private val A_COLOR = "a_Color"
    private val A_POSITION = "a_Position"
    private val U_MATRIX = "u_Matrix"

    private var aColorLocation = 0  //location in gpu where shader color attribute is
    private var aPositionLocation = 0 //location in gpu where shader position attribute is
    private var uMatrixLocation = 0 //location in gpu where shader matrix attribute is
    private var program: Int = 0 //location in gpu where program is that contains the fragment and vertexShader


    private val projectionMatrix =
        FloatArray(16) //the Matrix values that are used to transform openGL coordinate system to

    private val modelMatrix = FloatArray(16)

    private val vertexData: FloatBuffer //used to store tableVertice in native memore for openGL to access

    private val tableVertices = floatArrayOf(
// Order of coordinates: X, Y, Z, W, R, G, B
// Triangle Fan
        0f, 0f, 1f, 1f, 1f,
        -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
        0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
        0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
        -0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
        -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
// Line 1
        -0.5f, 0f, 1f, 0f, 0f,
        0.5f, 0f, 1f, 0f, 0f,
// Mallets
        0f, -0.4f, 0f, 0f, 1f,
        0f, 0.4f, 1f, 0f, 0f
    )

    init {
        vertexData = ByteBuffer.allocateDirect(tableVertices.count() * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        vertexData.put(tableVertices) //first you allocate the memory to use, then you put you object in that memory with .put
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f) //setup the color to clear to when you clear the screen

        val vertexCode =
            TextResourceReader.readResourceFromID(_context, R.raw.simple_vertex_shader) //access code from resources
        val fragmentCode = TextResourceReader.readResourceFromID(_context, R.raw.simple_fragment_shader)

        val vertexHandle =
            ShaderHelper.compileVertexShader(vertexCode) //compile and check if the compilation was successfull. return the handle in the gpu for later use
        val fragmentHandle =
            ShaderHelper.compileFragmentShader(fragmentCode) //compile and check if the compilation was successfull. return the handle in the gpu for later use

        program = ShaderHelper.linkProgram(
            vertexHandle,
            fragmentHandle
        ) //create a program to use, attach shaders and link themm then check that they linked successfully
        ShaderHelper.validateProgram(program)

        GLES30.glUseProgram(program) //open gl can store many programs we have to tell openGL to use the program after we have created it

        aPositionLocation = GLES30.glGetAttribLocation(
            program,
            A_POSITION
        ) //now that the program was created and the shaders were passed to the GPU we want the location of them for later use
        aColorLocation = GLES30.glGetAttribLocation(program, A_COLOR)
        uMatrixLocation = GLES30.glGetUniformLocation(program, U_MATRIX)

        vertexData.position(0) //Float buffers need to be told what their position is before use

        GLES30.glVertexAttribPointer( //we need to associate tableVertice items and their position in the table with shader attributes. it tells open gl where to find data for a_Position
            aPositionLocation,
            POSITION_COMPONENT_COUNT,
            GLES30.GL_FLOAT,
            false,
            stride,
            vertexData
        )

        GLES30.glEnableVertexAttribArray(aPositionLocation) //and then we need to enable those attributes

        vertexData.position(POSITION_COMPONENT_COUNT)
        GLES30.glVertexAttribPointer(
            aColorLocation,
            COLOR_COMPONENT_COUNT,
            GLES30.GL_FLOAT,
            false,
            stride,
            vertexData
        )
        GLES30.glEnableVertexAttribArray(aColorLocation)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height) //set the openGL View size

        perspectiveM(projectionMatrix,0, 45f, width.toFloat() / height.toFloat(), 1f, 10f) //sets matrix values to align with
        // a 45 degree view with a certain aspect and sets the near and far distances of the frustrum

        setIdentityM(modelMatrix, 0) //sets modelMatrix to use the identity transformation so that we can translate correctly in the next line
        translateM(modelMatrix, 0, 0f, 0f, -10.0000f) //sets the matrix values to translate the model into the screen by 2.5 so we can see it
        rotateM(modelMatrix, 0, 0f, 1f, 0f, 0f) //multiplies the model matrix previous values to rotate along the X axis negative 60 degrees

        val temp = FloatArray(16) //we need to create a temporary matrix for multiplication

        multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0) //multiply project and model matrix together into the temp matrix
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.count()) //then copy over and replace values for the temp Matrix into the projection Matrix

//
//        val widthF = width.toFloat()
//        val heightF = height.toFloat()
//
//        val aspectRatio =
//            if (widthF > heightF) { //get view aspect ratio. this value will be the same regardless of orientation
//                widthF / heightF
//            } else {
//                heightF / widthF
//            }
//
//
//        if (widthF > height) {
//            // Landscape
//            orthoM(
//                projectionMatrix,
//                0,
//                -aspectRatio,
//                aspectRatio,
//                -1f,
//                1f,
//                -1f,
//                1f
//            ) //create matrix that will later be used to tell openGL to change coordinate system to adjust for aspect ratio
//        } else {
//            // Portrait or square
//            orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f)
//        }
    }


    override fun onDrawFrame(gl: GL10?) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT) //clears the screen to the previously set color

        GLES30.glUniformMatrix4fv(
            uMatrixLocation,
            1,
            false,
            projectionMatrix,
            0
        ) //passes matrix to shader which then tells openGL to adjust vertices with model, rotations, and perspective matrices that have been multiplied together

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, 6)
        GLES30.glDrawArrays(GLES30.GL_LINES, 6, 2)
        GLES30.glDrawArrays(GLES30.GL_POINTS, 8, 2)
    }
}