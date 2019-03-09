package com.di.penopllast.xmltranslater.presentation.ui.screen.s2_choose_file.presenter

import com.di.penopllast.xmltranslater.presentation.controller.presenter.BasePresenter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s2_choose_file.view.ChooseFileFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class ChooseFilePresenterImpl(val view: ChooseFileFragment?)
    : BasePresenter(), ChooseFilePresenter {

    override fun checkFileExist() {
        scopeIO.launch {
            val filePath = repositoryPreference.getFilePath()
            if (!filePath.isEmpty()) {
                val file = File(filePath)
                if (file.exists()) {
                    withContext(Dispatchers.Main) { view?.showSavedFile(filePath) }
                }
            }
        }
    }


    override fun saveFileType(selectedItemPosition: Int) {
        scopeIO.launch { repositoryPreference.setFileType(selectedItemPosition) }
    }

    override fun saveFilePath(path: String) {
        scopeIO.launch { repositoryPreference.setFilePath(path) }
    }
}