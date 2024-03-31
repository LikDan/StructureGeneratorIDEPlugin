package likco.plugins.structuregenerator.utils

import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey
import likco.plugins.structuregenerator.Bundle

@NonNls
private const val BUNDLE = "messages.Bundle"

class BundleMessageException(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any) :
    Exception(Bundle.message(key, *params))