package likco.plugins.structuregenerator.handlers.dialogs

import com.intellij.execution.util.EnvVariablesTable
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.components.Label
import com.intellij.ui.components.panels.VerticalBox
import likco.plugins.structuregenerator.Bundle
import likco.plugins.structuregenerator.utils.createSingleFileDescriptor
import java.awt.Dimension
import javax.swing.Box
import javax.swing.JComponent


class GenerateDialog(private val project: Project?, private val workingDir: String) : DialogWrapper(project) {
    val data: GenerateDialogData
        get() = GenerateDialogData(
            directoryName = this.directoryNamePicker.text,
            configFilePath = this.configFilePicker.text,
            variables = this.varsTable.environmentVariables.associate { it.name to it.value }
        )

    private val directoryNamePicker = TextFieldWithBrowseButton().apply {
        val descriptorFactory = FileChooserDescriptorFactory.createSingleFolderDescriptor()
        this.addBrowseFolderListener(
            Bundle.message("dialogs.generate.config_file_picker.title"),
            Bundle.message("dialogs.generate.config_file_picker.description"),
            this@GenerateDialog.project,
            descriptorFactory
        )

        this.maximumSize = Dimension(this.maximumSize.width, this.preferredSize.height)

        this.text = this@GenerateDialog.workingDir
    }

    private val configFilePicker = TextFieldWithBrowseButton().apply {
        val descriptorFactory = createSingleFileDescriptor("json", "yaml", "yml", "xml")
        this.addBrowseFolderListener(
            Bundle.message("dialogs.generate.working_directory.title"),
            Bundle.message("dialogs.generate.working_directory.description"),
            this@GenerateDialog.project,
            descriptorFactory
        )

        this.maximumSize = Dimension(this.maximumSize.width, this.preferredSize.height)
    }

    private val varsTable = EnvVariablesTable()

    override fun createCenterPanel(): JComponent = VerticalBox().apply {
        add(Label(Bundle.message("dialogs.generate.working_directory")))
        add(Box.createRigidArea(Dimension(0, 5)))
        add(this@GenerateDialog.directoryNamePicker)
        add(Box.createRigidArea(Dimension(0, 5)))
        add(Label(Bundle.message("dialogs.generate.config_select")))
        add(Box.createRigidArea(Dimension(0, 5)))
        add(this@GenerateDialog.configFilePicker)
        add(Box.createRigidArea(Dimension(0, 5)))
        add(Label(Bundle.message("dialogs.generate.variables")))
        add(Box.createRigidArea(Dimension(0, 5)))
        add(varsTable.component)
    }

    init {
        init()
        this.title = Bundle.message("dialogs.generate.title")
        this.setSize(500, 450)
    }
}
