package likco.plugins.structuregenerator.repositories.config.deserializers

import java.io.InputStream
import javax.xml.bind.JAXBContext
import kotlin.reflect.KClass

class XmlDeserializer<T : Any>(private val objectClass: KClass<T>) : Deserializer<T> {
    @Suppress("UNCHECKED_CAST")
    override fun deserialize(stream: InputStream): T =
        JAXBContext.newInstance(objectClass.java).createUnmarshaller().unmarshal(stream) as T

    companion object {
        inline fun <reified T : Any> deserializer() = XmlDeserializer(T::class)
    }
}