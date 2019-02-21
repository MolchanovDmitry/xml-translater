package com.di.penopllast.xmltranslater.domain.model.string

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StringRoot(

        @SerializedName("resources")
        @Expose
        val resources: Resources? = null

)
