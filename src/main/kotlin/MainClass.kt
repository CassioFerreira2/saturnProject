package org.saturnProject2

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryUtil.NULL
import org.lwjgl.system.Configuration
import org.lwjgl.opengles.GLES
import org.lwjgl.opengles.GLES20.*
import org.lwjgl.opengl.GL
import org.lwjgl.BufferUtils

object MainClass {
    
    private fun init() {
        glfwInit()

        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)
        glfwWindowHint(GLFW_CLIENT_API, GLFW_OPENGL_ES_API)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0)

        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_ANY_PROFILE)

        val window = glfwCreateWindow(640, 480, "SolarSystem?", NULL, NULL)
        Configuration.OPENGLES_EXPLICIT_INIT.set(true)
        GLES.create(GL.getFunctionProvider()!!)
        glfwMakeContextCurrent(window)
        GLES.createCapabilities()

        val windowSize = BufferUtils.createIntBuffer()
        glfwGetWindowSize(window, windowSize[0], windowSize[1])

        while (!glfwWindowShouldClose(window)) {
            Renderer.render()
            glfwSwapBuffers(window)
            glfwPollEvents()
        }
        glfwTerminate()
        println("Até logo!!")
    }
    @JvmStatic
    fun main(array: Array<String>) {
        init()
    }


}