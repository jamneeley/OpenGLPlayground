uniform mat4 u_Matrix;   //matrix thats passed in
attribute vec4 a_Position;   //position of vertex
attribute vec2 a_TextureCoordinates;  //coordinates of the texture for the vertex
varying vec2 v_TextureCoordinates;  // send the interpolated varying to the fragment shader
void main()
{
    v_TextureCoordinates = a_TextureCoordinates;
    gl_Position = u_Matrix * a_Position;
}