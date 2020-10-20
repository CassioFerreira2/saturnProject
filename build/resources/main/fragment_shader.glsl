precision mediump float;

uniform sampler2D samp;
varying vec2 textureCoord;

void main() {
    gl_FragColor = texture2D(samp, textureCoord);
}
