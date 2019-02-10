package com.di.penopllast.xmltranslater.presentation.ui.screen.s2_choose_file.presenter

interface ChooseFilePresenter {
    fun saveFilePath(path: String)
    fun checkFileExist()
    fun saveFileType(selectedItemPosition: Int)
}