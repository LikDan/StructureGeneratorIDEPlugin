package likco.plugins.structuregenerator.repositories.config.deserializers

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.InputStream
import kotlin.reflect.KClass

class JsonDeserializer<T : Any>(private val objectClass: KClass<T>) : Deserializer<T> {
    override fun deserialize(stream: InputStream): T =
        ObjectMapper().readValue(stream, this.objectClass.java)

    companion object {
        inline fun <reified T : Any> deserializer() = JsonDeserializer(T::class)
    }
}