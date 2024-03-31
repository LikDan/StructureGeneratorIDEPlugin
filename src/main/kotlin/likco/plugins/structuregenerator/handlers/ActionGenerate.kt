/*
 * Copyright: Copyright (c) 2019 Arne Rantzen <arne@rantzen.net>
 * License: GPL-3
 * Last Edited: 08.12.19, 00:03
 */

package likco.plugins.structuregenerator.handlers

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import likco.plugins.structuregenerator.Bundle
import likco.plugins.structuregenerator.controllers.Controller
import likco.plugins.structuregenerator.controllers.dto.RequestGenerateDTO
import likco.plugins.structuregenerator.handlers.dialogs.GenerateDialog
import likco.plugins.structuregenerator.handlers.dialogs.GenerateDialogData

class ActionGenerate : AnAction() {
    override fun actionPerformed(actionEvent: AnActionEvent) {
        val dataContext = actionEvent.dataContext
        val selected = PlatformDataKeys.VIRTUAL_FILE.getData(dataContext) ?: return
        val workingDir = if (selected.isDirectory) selected else selected.parent

        val dialog = GenerateDialog(actionEvent.project, workingDir.path)
        if (!dialog.showAndGet()) return

        generate(workingDir.path, actionEvent.project, dialog.data)
    }

    private fun generate(workingDir: String, project: Project?, data: GenerateDialogData) {
        WriteCommandAction.runWriteCommandAction(project) {
            runCatching {
                val request = RequestGenerateDTO(
                    workDir = workingDir,
                    directoryName = data.directoryName,
                    configFilePath = data.configFilePath,
                    variables = data.variables,
                )

                Controller.generate(request)
            }.onFailure {
                Messages.showErrorDialog(
                    it.message,
                    Bundle.message("dialogs.error.title"),
                )
            }
        }
    }
}