package com.premium.aboutyou.net

import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

/** Small OkHttp wrapper. All calls are blocking; call them from Dispatchers.IO. */
object Http {

    const val UA = "AboutYou/1.0 (Android; +https://example.org/aboutyou)"

    val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(40, TimeUnit.SECONDS)
        .callTimeout(50, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    fun get(url: String): String? = try {
        val req = Request.Builder()
            .url(url)
            .header("User-Agent", UA)
            .header("Accept", "application/json")
            .build()
        client.newCall(req).execute().use { resp ->
            if (!resp.isSuccessful) null else resp.body?.string()
        }
    } catch (t: Throwable) {
        null
    }

    fun getBytes(url: String): ByteArray? = try {
        val req = Request.Builder().url(url).header("User-Agent", UA).build()
        client.newCall(req).execute().use { resp ->
            if (!resp.isSuccessful) null else resp.body?.bytes()
        }
    } catch (t: Throwable) {
        null
    }

    fun enc(s: String): String = java.net.URLEncoder.encode(s, "UTF-8")
}
