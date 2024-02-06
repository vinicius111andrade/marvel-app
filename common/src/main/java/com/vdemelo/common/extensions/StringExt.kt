package com.vdemelo.common.extensions

fun String.simpleCapitalize(): String = this.replaceFirstChar(Char::titlecase)
