package likco.plugins.structuregenerator.controllers

import likco.plugins.structuregenerator.controllers.dto.RequestGenerateDTO
import likco.plugins.structuregenerator.controllers.models.GenerationInfo
import likco.plugins.structuregenerator.repositories.command.CommandRepository
import likco.plugins.structuregenerator.repositories.config.ConfigRepository
import likco.plugins.structuregenerator.repositories.config.entities.ConfigItem
import likco.plugins.structuregenerator.repositories.config.entities.ConfigItemType
import likco.plugins.structuregenerator.repositories.filesystem.FileSystemRepository
import likco.plugins.structuregenerator.utils.BundleMessageException
import java.io.File

object Controller {
    fun generate(request: RequestGenerateDTO) {
        val configFile = File(request.configFilePath)
        if (!configFile.exists()) throw BundleMessageException("errors.config_file_not_found")

        val config = ConfigRepository.getConfigFromFile(configFile)

        val workDir = request.directoryName
        FileSystemRepository.createDir(workDir)

        val variables = request.variables.toMutableMap()
        config.vars.forEach {
            if (request.variables.containsKey(it.key)) return@forEach

            variables[it.key] = it.value
        }

        val generationInfo = GenerationInfo(
            workDir = workDir,
            variables = variables,
        )

        config.items.forEach { processItem(generationInfo, it) }

        if (config.command != "") {
            val command = fillStringWithVariables(config.command, variables)
            CommandRepository.exec(request.workDir, command)
        }
    }

    private fun processItem(request: GenerationInfo, item: ConfigItem) {
        when (item.type) {
            ConfigItemType.FOLDER -> processFolder(request, item)
            ConfigItemType.FILE -> processFile(request, item)
        }
    }

    private fun processFolder(request: GenerationInfo, item: ConfigItem) {
        val path = fillStringWithVariables("${request.workDir}/${item.name}", request.variables)
        FileSystemRepository.createDir(path)
    }

    private fun processFile(request: GenerationInfo, item: ConfigItem) {
        var content = when {
            item.file?.content != null -> item.file.content
            item.file?.contentFile != null -> File(request.workDir, item.file.contentFile).readText()
            else -> ""
        }
        content = fillStringWithVariables(content, request.variables)

        val path = fillStringWithVariables("${request.workDir}/${item.name}", request.variables)
        FileSystemRepository.createFile(path, content)
    }

    private fun fillStringWithVariables(string: String, vars: Map<String, String>): String {
        var result = string
        vars.forEach { (key, value) -> result = result.replace("{$key}", value) }
        result = result.replace("\\{", "{")
        return result
    }
}