package com.di.penopllast.xmltranslater.presentation.ui.screen.s2_choose_file.presenter

import com.di.penopllast.xmltranslater.presentation.controller.presenter.BasePresenter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s2_choose_file.view.ChooseFileFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class ChooseFilePresenterImpl(val view: ChooseFileFragment)
    : BasePresenter(), ChooseFilePresenter {

    override fun checkFileExist() {
        GlobalScope.launch {
            val filePath = repositoryPreference.getFilePath()
            if (!filePath.isEmpty()) {
                val file = File(filePath)
                if (file.exists()) {
                    view.showSavedFile(filePath)
                }
            }
        }
    }


    override fun saveFileType(selectedItemPosition: Int) {
        GlobalScope.launch { repositoryPreference.setFileType(selectedItemPosition) }
    }

    override fun saveFilePath(path: String) {
        GlobalScope.launch { repositoryPreference.setFilePath(path) }
    }

}