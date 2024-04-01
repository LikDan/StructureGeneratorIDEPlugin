package likco.plugins.structuregenerator.repositories.config.entities

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement


@XmlRootElement(name = "config")
data class Config(
    @field:XmlElement(name = "items")
    val items: MutableList<ConfigItem> = mutableListOf(),
    @field:XmlElement(name = "vars")
    val vars: MutableList<ConfigVar> = mutableListOf(),
    @field:XmlElement(name = "command")
    val command: String = "",
)

enum class ConfigItemType {
    FOLDER,
    FILE,
}

data class ConfigItem(
    @field:XmlElement(name = "type")
    val type: ConfigItemType = ConfigItemType.FOLDER,
    @field:XmlElement(name = "name")
    val name: String = "",
    @field:XmlElement(name = "file")
    val file: ConfigFile? = null,
)

data class ConfigFile(
    @field:XmlElement(name = "content")
    val content: String? = null,
    @field:XmlElement(name = "contentFile")
    val contentFile: String? = null,
)

data class ConfigVar(
    @field:XmlElement(name = "key")
    val key: String = "",
    @field:XmlElement(name = "value")
    val value: String = "",
)
