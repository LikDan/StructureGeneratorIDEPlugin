package likco.plugins.structuregenerator.repositories.config.deserializers

import java.io.InputStream

interface Deserializer <T : Any>  {
    fun deserialize(stream: InputStream): T
}