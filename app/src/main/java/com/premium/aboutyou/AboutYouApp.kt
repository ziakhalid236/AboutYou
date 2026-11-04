package com.premium.aboutyou

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.premium.aboutyou.net.Http

/**
 * Wikimedia rejects requests that do not carry a descriptive User-Agent,
 * so image loading reuses the same client identity as the API calls.
 */
class AboutYouApp : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader =
        ImageLoader.Builder(this)
            .okHttpClient { Http.client }
            .crossfade(true)
            .build()
}
