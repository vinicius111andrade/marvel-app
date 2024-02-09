package com.vdemelo.marvel.data.remote.network.interceptor

import com.vdemelo.marvel.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.and
import java.security.MessageDigest

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val ts = "${System.currentTimeMillis() / 1000}"
        val privateKey = BuildConfig.PRIVATE_API_KEY
        val publicKey = BuildConfig.PUBLIC_API_KEY
        val url = chain.request().url
            .newBuilder()
            .addQueryParameter("ts", ts)
            .addQueryParameter("apikey", publicKey)
            .addQueryParameter("hash", convertMD5(ts + privateKey + publicKey))
            .build()
        val request: Request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }

    private fun convertMD5(str: String): String {
        return try {
            val messageDigest: MessageDigest = MessageDigest.getInstance("MD5")
            messageDigest.update(str.toByteArray(charset("UTF-8")))
            val byteData: ByteArray = messageDigest.digest()
            val stringBuffer = StringBuffer()
            for (i in byteData.indices) stringBuffer.append(
                ((byteData[i] and 0xff) + 0x100).toString(16).substring(1)
            )
            stringBuffer.toString()
        } catch (e: Exception) {
            ""
        }
    }
}
