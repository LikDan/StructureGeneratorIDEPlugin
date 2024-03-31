package likco.plugins.structuregenerator.repositories.filesystem

import java.io.File

object FileSystemRepository {
    fun createDir(path: String) {
        File(path).mkdirs()
    }

    fun createFile(path: String, content: String) {
        File(path).apply { this.parentFile.mkdirs() }.writeText(content)
    }
}