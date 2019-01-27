package com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.view

import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.data.ExtendedLocaleMatch

interface ChooseDestinationLanguagesFragment {
    fun showExtendedLocaleMatchList(extendedLocaleMatchList: ArrayList<ExtendedLocaleMatch>)
    fun toTranslateFragment()

}