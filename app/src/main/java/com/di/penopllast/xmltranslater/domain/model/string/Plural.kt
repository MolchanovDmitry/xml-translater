package com.di.penopllast.xmltranslater.domain.model.string

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Plural(

        @SerializedName("item")
        @Expose
        val item: List<Item>? = null,
        @SerializedName("name")
        @Expose
        val name: kotlin.String? = null
)
