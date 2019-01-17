package com.di.penopllast.xmltranslater.domain.model.string

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class String(

        @SerializedName("content")
        @Expose
        var content: kotlin.String? = null,
        @SerializedName("name")
        @Expose
        var name: kotlin.String? = null,
        @SerializedName("translatable")
        @Expose
        var translatable: kotlin.String? = null
)