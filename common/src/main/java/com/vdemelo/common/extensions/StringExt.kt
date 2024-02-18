package com.vdemelo.common.extensions

fun String.simpleCapitalize(): String = this.replaceFirstChar(Char::titlecase)

fun String?.isNotNullOrBlank(): Boolean = !this.isNullOrBlank()

fun List<String?>.toCharSum(): Long { //TODO create unit test for these :)
    var sum: Long = 0
    this.forEach { string ->
        if (string.isNotNullOrBlank()) {
            val charArray = string!!.toCharArray()
            charArray.forEach { char ->
                sum += char.code
            }
        }
    }
    return sum
}

fun String?.ifNullOrEmpty(placeholder: String): String {
    return if (this.isNullOrEmpty()) {
        placeholder
    } else {
        this
    }
}
