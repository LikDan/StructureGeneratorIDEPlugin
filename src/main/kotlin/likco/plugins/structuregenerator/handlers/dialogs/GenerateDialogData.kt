package likco.plugins.structuregenerator.handlers.dialogs

data class GenerateDialogData(
    val directoryName: String,
    val configFilePath: String,
    val variables: Map<String, String>,
)