package com.di.penopllast.xmltranslater.domain.model.string

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Integer(
        @SerializedName("content")
        @Expose
        var content: StringRow? = null,
        @SerializedName("name")
        @Expose
        var name: StringRow? = null
)
