package org.saturnProject2

import org.lwjgl.opengles.GLES20.*
import java.nio.ByteBuffer

object Renderer {
    private val program: Int

    init {

        val vertexCode = readFile("vertex_shader.glsl")
        val fragmentCode = readFile("fragment_shader.glsl")

        program = compAndBuild(vertexCode, fragmentCode)
        glUseProgram(program)

        // Set color to semi-white
        glClearColor(0.8f, 0.8f, 0.8f, 0.8f)
    }

    fun render() {
        glClear(GL_COLOR_BUFFER_BIT)

        Saturn.draw(program)
    }
}