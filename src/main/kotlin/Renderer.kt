package org.saturnProject2

import org.lwjgl.opengles.GLES20.*
import java.nio.ByteBuffer

object Renderer {
    private val program: Int

    private val projectionMatrixLocation: Int
    private val viewMatrixLocation: Int
    private val modelMatrixLocation: Int

    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val modelMatrix = FloatArray(16)

    private var move = true
    private var moveVelocity = 0.05f
    private var zoomVelocity = 0.02f

    init {

        val vertexCode = readFile("vertex_shader.glsl")
        val fragmentCode = readFile("fragment_shader.glsl")

        program = compAndBuild(vertexCode, fragmentCode)
        glUseProgram(program)

        // Set color to semi-white
        glClearColor(0.8f, 0.8f, 0.8f, 0.8f)

        projectionMatrixLocation = getAndVerifyUniform(program, "u_projectionMatrix")
        viewMatrixLocation = getAndVerifyUniform(program, "u_viewMatrix")
        modelMatrixLocation = getAndVerifyUniform(program, "u_modelMatrix")

        // Deve fazer com que o opengl possa ler a matriz
        setIdentityMatrix(projectionMatrix)
        setIdentityMatrix(viewMatrix)
        setIdentityMatrix(modelMatrix)

    }

    fun render(width: Int, height: Int) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        doVerifications()

        Sun.draw(program, modelMatrix)

        Sun.rotating = true

        glUniformMatrix4fv(projectionMatrixLocation, false, projectionMatrix)
        glUniformMatrix4fv(viewMatrixLocation, false, viewMatrix)
        glUniformMatrix4fv(modelMatrixLocation, false, modelMatrix)

        val aspectRatio = width.toFloat() / height.toFloat()
        glViewport(0, 0, width, (height * aspectRatio).toInt())

    }

    fun doVerifications() {
        if (move) doMovesOfKeyboard(Events)

    }

    fun doMovesOfKeyboard(e: Events) {
        when {
            // Mover ao redor do mundo
            e.arrowRight -> moveRight(moveVelocity, viewMatrix)
            e.arrowLeft  -> moveLeft(moveVelocity, viewMatrix)
            e.arrowDown  -> moveDown(moveVelocity, viewMatrix)
            e.arrowUp    -> moveUp(moveVelocity, viewMatrix)

            // Zoom
            e.keyEqual -> zoomIn(zoomVelocity, viewMatrix)
            e.keyMinus -> zoomOut(zoomVelocity, viewMatrix)
        }
    }
}