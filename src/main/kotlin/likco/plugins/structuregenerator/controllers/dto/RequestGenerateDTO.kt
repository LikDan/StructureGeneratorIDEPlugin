package likco.plugins.structuregenerator.controllers.dto

data class RequestGenerateDTO(
    val workDir: String,
    val directoryName: String,
    val configFilePath: String,
    val variables: Map<String, String>
)
