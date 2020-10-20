attribute vec4 a_Position;
attribute vec2 a_textureCoord;

varying vec2 textureCoord;

void main() {
    textureCoord = a_textureCoord;
    gl_Position = a_Position;
}
