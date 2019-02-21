package com.di.penopllast.xmltranslater.domain.model.string

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StringRow(

        @SerializedName("content")
        @Expose
        val content: kotlin.String,
        @SerializedName("name")
        @Expose
        val name: kotlin.String,
        @SerializedName("translatable")
        @Expose
        val translatable: kotlin.String? = null
)