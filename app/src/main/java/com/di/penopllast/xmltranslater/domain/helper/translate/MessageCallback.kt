package com.di.penopllast.xmltranslater.domain.helper.translate

interface MessageCallback {
    fun onStatusRetrieved(index: Int, count: Int)
    fun onTranslateMessageRetrieved(message: String, @MessageType type: Int)
}