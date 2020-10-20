package org.saturnProject2

import kotlin.math.cos
import kotlin.math.sin

// Este arquivo será de algumas funções de matriz
// favor, não mecher em funções prontas

// :)

const val PI = 3.141592f

fun setIdentityMatrix(m: FloatArray) {
    m[0]    = 1f
    m[1]    = 0f
    m[2]    = 0f
    m[3]    = 0f
    m[4]    = 0f
    m[5]    = 1f
    m[6]    = 0f
    m[7]    = 0f
    m[8]    = 0f
    m[9]    = 0f
    m[10]   = 1f
    m[11]   = 0f
    m[12]   = 0f
    m[13]   = 0f
    m[14]   = 0f
    m[15]   = 1f
}

// Rotaciona no eixo XY
var angle = 0f
fun rotateSquare(vel: Float, modelMatrix: FloatArray) {
    // Temos que primeiro passar para radianos
    val radians = toRadians(angle)

    modelMatrix[0] =  cos(radians)
    modelMatrix[1] =  sin(radians)
    modelMatrix[4] = -sin(radians)
    modelMatrix[5] =  cos(radians)
    angle += vel
}

// Transforma graus para radianos
fun toRadians(angle: Float): Float {
    return angle * PI / 180
}

// Algumas funções para mover a tela
fun moveRight(vel: Float, viewMatrix: FloatArray) {
    viewMatrix[12] -= vel
}
fun moveLeft(vel: Float, viewMatrix: FloatArray) {
    viewMatrix[12] += vel
}
fun moveDown(vel: Float, viewMatrix: FloatArray) {
    viewMatrix[13] += vel
}
fun moveUp(vel: Float, viewMatrix: FloatArray) {
    viewMatrix[13] -= vel
}

// Funções para zoom-in e zoom-out
fun zoomIn(vel: Float, viewMatrix: FloatArray) {
    viewMatrix[15] -= vel
}
fun zoomOut(vel: Float, viewMatrix: FloatArray) {
    viewMatrix[15] += vel
}