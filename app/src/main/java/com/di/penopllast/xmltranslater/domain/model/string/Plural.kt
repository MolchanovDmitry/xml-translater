package com.di.penopllast.xmltranslater.domain.model.string

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Plural(

        @SerializedName("item")
        @Expose
        var item: List<Item>? = null,
        @SerializedName("name")
        @Expose
        var name: kotlin.String? = null
)
