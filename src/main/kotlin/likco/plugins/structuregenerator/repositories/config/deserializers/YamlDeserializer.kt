package likco.plugins.structuregenerator.repositories.config.deserializers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.InputStream
import kotlin.reflect.KClass

class YamlDeserializer<T : Any>(private val objectClass: KClass<T>) : Deserializer<T> {
    override fun deserialize(stream: InputStream): T =
        ObjectMapper(YAMLFactory()).readValue(stream, this.objectClass.java)

    companion object {
        inline fun <reified T : Any> deserializer() = YamlDeserializer(T::class)
    }
}