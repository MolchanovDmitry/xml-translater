package com.di.penopllast.xmltranslater.domain.model.string

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Item(
        @SerializedName("content")
        @Expose
        var content: kotlin.String? = null,
        @SerializedName("quantity")
        @Expose
        var quantity: kotlin.String? = null
)
