package com.di.penopllast.xmltranslater.domain.model.string

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Resources(

        @SerializedName("string")
        @Expose
        var string: List<StringRow>? = null,
        @SerializedName("plurals")
        @Expose
        var plurals: List<Plural>? = null,
        @SerializedName("integer")
        @Expose
        var integer: Any? = null
)
