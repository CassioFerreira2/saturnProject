@file:Suppress("UNUSED_VARIABLE")

package org.saturnProject2
import org.lwjgl.opengles.GLES20.*
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

object Saturn {
    const val SIZE = 1f
    const val POSITION_COMPONENTS = 3
    const val COLOR_COMPONENTS = 4

    // Square
    val vertices = floatArrayOf(
            -1f, -1f, 0f,
            1f, -1f, 0f,
            1f,  1f, 0f,
            -1f,  1f, 0f
    )
    val indices = shortArrayOf(0, 1, 2, 2, 3, 0)

    val appleTextureCoords = floatArrayOf(
            // Triangle 1
            1f, 0f,
            1f, 1f,
            0f, 1f,
            0f, 0f
    )

    private val appleTexture: Int = buildTexture("apple.png", 900, 900)
    private val appleTextureCoordsID = buildStaticArrayVBOS(appleTextureCoords)

    private var a_PositionLocation: Int? = null


    init {
        // Multiplica pelo tamanho
        for ( i in vertices.indices ) {
            vertices[i] *= SIZE
        }


    }

    fun draw(program: Int) {
        drawTexture(program)

        a_PositionLocation = getAndVerifyAtributte(program, "a_Position")

        val VBO = buildStaticArrayVBOS(vertices)
        val IBO = buildStaticIndicesVBOS(indices)

        glBindBuffer(GL_ARRAY_BUFFER, VBO)
        glVertexAttribPointer(a_PositionLocation!!, POSITION_COMPONENTS, GL_FLOAT, false,
                                0, 0)
        glEnableVertexAttribArray(a_PositionLocation!!)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO)
        nglDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_SHORT, 0)
    }

    private fun drawTexture(program: Int) {
        // Consegue a localização do samplerm, ativa para textura 0,
        // víncula a textura da apple e linka o sampler pra textura 0
        val samplerLocation = getAndVerifyUniform(program, "samp")
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, appleTexture)
        glUniform1i(samplerLocation, 0)

        // Vamos tentar aqui víncular o buffer das coordenadas da textura
        // e ao mesmo tempo já passar para o programa as mesmas
        val textureCoordsLocation = getAndVerifyAtributte(program, "a_textureCoord")
        glBindBuffer(GL_ARRAY_BUFFER, appleTextureCoordsID)
        glVertexAttribPointer(textureCoordsLocation, 2, GL_FLOAT, false, 0, 0)
        glEnableVertexAttribArray(textureCoordsLocation)

    }

}