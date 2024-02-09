package com.vdemelo.common.extensions

fun String.simpleCapitalize(): String = this.replaceFirstChar(Char::titlecase)

fun String?.isNotNullOrBlank(): Boolean = !this.isNullOrBlank()
