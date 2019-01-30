package com.di.penopllast.xmltranslater.presentation.ui.s4_choose_languages.view

import com.di.penopllast.xmltranslater.presentation.ui.s4_choose_languages.data.ExtendedLocaleMatch

interface ChooseDestinationLanguagesFragment {
    fun showExtendedLocaleMatchList(extendedLocaleMatchList: ArrayList<ExtendedLocaleMatch>)
    fun toTranslateFragment()

}