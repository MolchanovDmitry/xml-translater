package com.di.penopllast.xmltranslater.domain.model.translate

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Translate(
        @SerializedName("code")
        @Expose
        var code: Int? = null,
        @SerializedName("lang")
        @Expose
        var lang: String? = null,
        @SerializedName("text")
        @Expose
        var text: List<String>? = null
)
