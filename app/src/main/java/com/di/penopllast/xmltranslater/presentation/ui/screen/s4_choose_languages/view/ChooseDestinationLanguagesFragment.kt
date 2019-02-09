package com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.view

import com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.model.ExtendedLocaleMatch

interface ChooseDestinationLanguagesFragment {
    fun showExtendedLocaleMatchList(extendedLocaleMatchList: ArrayList<ExtendedLocaleMatch>)
    fun toTranslateFragment()

}