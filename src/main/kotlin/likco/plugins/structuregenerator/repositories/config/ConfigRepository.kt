package likco.plugins.structuregenerator.repositories.config

import likco.plugins.structuregenerator.repositories.config.deserializers.Deserializer
import likco.plugins.structuregenerator.repositories.config.deserializers.JsonDeserializer
import likco.plugins.structuregenerator.repositories.config.deserializers.XmlDeserializer
import likco.plugins.structuregenerator.repositories.config.deserializers.YamlDeserializer
import likco.plugins.structuregenerator.repositories.config.entities.Config
import likco.plugins.structuregenerator.utils.BundleMessageException
import java.io.File


object ConfigRepository {
    fun getConfigFromFile(file: File): Config {
        val decoder = getDeserializerByExtension(file.extension)
        return decoder.deserialize(file.inputStream())
    }

    private fun getDeserializerByExtension(extension: String): Deserializer<Config> = when (extension) {
        "json" -> JsonDeserializer.deserializer<Config>()
        "yaml", "yml" -> YamlDeserializer.deserializer<Config>()
        "xml" -> XmlDeserializer.deserializer<Config>()
        else -> throw BundleMessageException("errors.unsupported_extension")
    }
}