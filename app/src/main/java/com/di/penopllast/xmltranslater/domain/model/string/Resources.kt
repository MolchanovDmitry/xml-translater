package com.di.penopllast.xmltranslater.domain.model.string

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Resources(

        @SerializedName("string")
        @Expose
        val string: List<StringRow>? = null,
        @SerializedName("plurals")
        @Expose
        val plurals: List<Plural>? = null,
        @SerializedName("integer")
        @Expose
        val integer: Any? = null
)
