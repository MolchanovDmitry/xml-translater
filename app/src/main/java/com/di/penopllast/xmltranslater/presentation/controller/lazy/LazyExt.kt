package com.di.penopllast.xmltranslater.presentation.controller.lazy

fun <T> lazyUnsychronized(initializer: () -> T):
        Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)