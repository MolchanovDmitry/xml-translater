package com.di.penopllast.xmltranslater.domain.model.translate

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Translate(
        @SerializedName("code")
        @Expose
        val code: Int? = null,
        @SerializedName("lang")
        @Expose
        val lang: String? = null,
        @SerializedName("text")
        @Expose
        val text: List<String>? = null
)
