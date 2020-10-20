precision mediump float;

uniform sampler2D samp;
varying vec2 textureCoord;

void main() {
    vec4 color = texture2D(samp, textureCoord);
    if (color.a < 0.25) {
        discard;
    }
    gl_FragColor = color;
}