package org.saturnProject2

import org.lwjgl.opengles.GLES20.*
import de.matthiasmann.twl.utils.PNGDecoder
import java.io.File
import java.io.FileInputStream
import java.nio.*
import javax.management.RuntimeErrorException


// Lê um arquivo
fun readFile(name: String): String {
    val local = "C:\\Users\\cassio\\Desktop\\otheropenglProjects\\saturnProject2\\src\\main\\resources\\$name"
    return File(local).readText()
}

// Compila o shader
fun compileShader(code: String, type: Int): Int {
    val shader = glCreateShader(type)
    if (shader == 0) println("Couldn't create the shader")
    glShaderSource(shader, code)
    glCompileShader(shader)
    val compiled = IntArray(1)                                      // Consegue o status do shader
    glGetShaderiv(shader, GL_COMPILE_STATUS, compiled)                  // Entrega para o array
    if (compiled[0] == 0) println("Couldn't compile the shader")
    println(glGetShaderInfoLog(shader))
    return shader
}

// Constrói o programa
fun buildProgram(vert: Int, frag: Int): Int {
    val program = glCreateProgram()
    if (program == 0) println("Couldn't create program")
    glAttachShader(program, vert)
    glAttachShader(program, frag)
    glLinkProgram(program)
    val linked = IntArray(1)
    glGetProgramiv(program, GL_LINK_STATUS, linked)
    if (linked[0] == 0) println("Couldn't link the program!")
    return program

}

// Faz as duas de cima, consegue tanto compilar quanto
// entregar o programa com verificações
fun compAndBuild(vertCode: String, fragCode: String): Int {
    val fragmentShader = compileShader(fragCode, GL_FRAGMENT_SHADER)
    val vertexShader   = compileShader(vertCode, GL_VERTEX_SHADER)
    return buildProgram(vertexShader, fragmentShader)
}

// Consegue a localização do atributo e verifica
fun getAndVerifyAtributte(program: Int, name: String): Int {
    val location = glGetAttribLocation(program, name)
    if (location == -1) throw RuntimeErrorException(Error("Não foi possível localizar atributo!"))
    return location
}

// Consegue a localização de uniforme e verifica
fun getAndVerifyUniform(program: Int, name: String): Int {
    val location = glGetUniformLocation(program, name)
    if (location == -1) throw RuntimeErrorException(Error("Não foi possível localizar uniforme!"))
    return location
}

// Constroi e linka um Vertex Buffer Object
fun buildStaticArrayVBOS(vertex: FloatArray): Int {
    val id = glGenBuffers()
    glBindBuffer(GL_ARRAY_BUFFER, id)
    glBufferData(GL_ARRAY_BUFFER, vertex, GL_STATIC_DRAW)
    if ( id < 1 ) println("Não foi possível criar VBOS")
    return id
}

// Constroi e linka um Indice Buffer Object
fun buildStaticIndicesVBOS(indices: ShortArray): Int {
    val id = glGenBuffers()
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id)
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)
    if ( id < 1 ) println("Não foi possível criar VBOS")
    return id
}

// Entrega o buffer de uma imagem PNG
// Pegando um código já feito porque nós é vagabundo msm :D
fun getByteBufferOfPNG(name: String): ByteBuffer {
    val buff = FileInputStream("C:\\Users\\cassio\\Desktop\\otheropenglProjects\\saturnProject2\\src\\main\\resources\\$name")
    val decoder = PNGDecoder(
            buff
    )

    val byteBuffer = ByteBuffer.allocateDirect(4 * decoder.width * decoder.height)
    decoder.decode(byteBuffer, decoder.width * 4, PNGDecoder.Format.RGBA)
    byteBuffer.flip()
    return byteBuffer
}

// Carrega a textura
fun loadTexture(byteBuffer: ByteBuffer, width: Int, height: Int): Int {
    val id = glGenTextures()
    glBindTexture(GL_TEXTURE_2D, id)

    glPixelStorei(GL_UNPACK_ALIGNMENT, id)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height,
                0, GL_RGBA, GL_UNSIGNED_BYTE, byteBuffer)
    if ( id < 1 ) println("Não foi possível criar textura!")
    glGenerateMipmap(GL_TEXTURE_2D)
    return id
}

// Constrói a textura, faz as 2 funções lá de cima
fun buildTexture(name: String, width: Int, height: Int): Int {
    val textureBuffer = getByteBufferOfPNG(name)
    return loadTexture(textureBuffer, width, height)
}