package likco.plugins.structuregenerator.utils

import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.util.Comparing
import com.intellij.openapi.vfs.VirtualFile

fun createSingleFileDescriptor(vararg extensions: String): FileChooserDescriptor =
    FileChooserDescriptor(true, false, false, false, false, false)
        .withFileFilter { file: VirtualFile ->
            extensions.any {
                @Suppress("UnstableApiUsage")
                Comparing.equal(file.extension, it, file.isCaseSensitive)
            }
        }