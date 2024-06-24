package com.mati.mimovies.features.movies.presenter.util.MediaPlayer

import android.content.Context
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.CacheEvictor
import com.google.android.exoplayer2.util.Util


private const val MAX_CACHE_SIZE_BYTES: Long = 100 * 1024 * 1024 // 100 MB in bytes

fun createCache(context: Context): Cache {
    val cacheDirectory = File(context.cacheDir, "media_cache")
    val cacheEvictor = LeastRecentlyUsedCacheEvictor(MAX_CACHE_SIZE_BYTES)
    val databaseProvider = ExoDatabaseProvider(context)

    return SimpleCache(cacheDirectory, cacheEvictor, databaseProvider)
}

fun buildDataSourceFactory(context: Context, cache: Cache): DataSource.Factory {
    val upstreamFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "YourApplicationName"))

    return object : DataSource.Factory {
        override fun createDataSource(): DataSource {
            return CacheDataSource(
                cache,
                upstreamFactory.createDataSource(),
                CacheDataSource.FLAG_BLOCK_ON_CACHE or CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR
            )
        }
    }
}

object CacheManager {
    private var simpleCache: SimpleCache? = null

    @Synchronized
    fun getCache(context: Context): SimpleCache {
        if (simpleCache == null) {
            val cacheFolder = File(context.cacheDir, "media_cache")
            val evictor: CacheEvictor = LeastRecentlyUsedCacheEvictor(100 * 1024 * 1024) // 100 MB maximum cache size
            simpleCache = SimpleCache(cacheFolder, evictor)
        }
        return simpleCache!!
    }
}
