package com.di.penopllast.xmltranslater.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap

data class RootLangs(
        @SerializedName("dirs")
        @Expose
        val dirs: List<String>,
        @SerializedName("langs")
        @Expose
        val langs: LinkedTreeMap<String, String>
)
