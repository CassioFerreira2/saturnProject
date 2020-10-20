@file:Suppress("UNUSED_PARAMETER")

package org.saturnProject2

import org.lwjgl.glfw.GLFW.*

object Events {
    var arrowLeft = false
    var arrowRight = false
    var arrowDown = false
    var arrowUp = false
    var keyMinus = false
    var keyEqual = false

    fun keyHandler(un1: Long, key: Int, un2: Int, action: Int, un4: Int) {
        when (key) {
            GLFW_KEY_LEFT -> arrowLeft = true
            GLFW_KEY_RIGHT -> arrowRight = true
            GLFW_KEY_DOWN -> arrowDown = true
            GLFW_KEY_UP -> arrowUp = true
            GLFW_KEY_MINUS -> keyMinus = true
            GLFW_KEY_EQUAL -> keyEqual = true
        }
        if (action == GLFW_RELEASE) resetKeys()

    }
    fun resetKeys() {
        arrowLeft = false
        arrowRight = false
        arrowDown = false
        arrowUp = false
        keyMinus = false
        keyEqual = false
    }

}