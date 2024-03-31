package likco.plugins.structuregenerator.controllers.models

data class GenerationInfo(
    val workDir: String,
    val variables: Map<String, String>,
)
