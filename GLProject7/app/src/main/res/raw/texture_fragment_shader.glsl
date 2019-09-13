precision mediump float;
uniform sampler2D u_TextureUnit; //sampler2D is an array of two dimensional texture data
varying vec2 v_TextureCoordinates;  //varying vec2 sent from vertexShader
void main()
{
    gl_FragColor = textudsre2D(u_TextureUnit, v_TextureCoordinates);
}