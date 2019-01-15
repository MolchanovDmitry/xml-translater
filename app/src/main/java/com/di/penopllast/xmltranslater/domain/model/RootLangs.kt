package com.di.penopllast.xmltranslater.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RootLangs(
        @SerializedName("dirs")
        @Expose
        val dirs: List<String>,
        @SerializedName("langs")
        @Expose
        val langs: Any
)
