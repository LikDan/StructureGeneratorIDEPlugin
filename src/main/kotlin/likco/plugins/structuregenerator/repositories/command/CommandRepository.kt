package likco.plugins.structuregenerator.repositories.command

import java.io.File

object CommandRepository {
    fun exec(workingDir: String, command: String) {
        ProcessBuilder(*command.split(" ").toTypedArray())
            .directory(File(workingDir))
            .start()
    }
}