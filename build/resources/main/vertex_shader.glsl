attribute vec4 a_Position;
attribute vec2 a_textureCoord;

varying vec2 textureCoord;

uniform mat4 u_projectionMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_modelMatrix;

void main() {
    textureCoord = a_textureCoord;
    gl_Position = u_projectionMatrix * u_viewMatrix * u_modelMatrix * a_Position;
}
