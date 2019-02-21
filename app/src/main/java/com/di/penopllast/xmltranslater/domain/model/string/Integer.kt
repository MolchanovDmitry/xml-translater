package com.di.penopllast.xmltranslater.domain.model.string

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Integer(
        @SerializedName("content")
        @Expose
        val content: StringRow? = null,
        @SerializedName("name")
        @Expose
        val name: StringRow? = null
)
