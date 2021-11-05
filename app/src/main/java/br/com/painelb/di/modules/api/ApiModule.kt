package br.com.painelb.di.modules.api

import br.com.painelb.BuildConfig
import br.com.painelb.api.ApiService
import br.com.painelb.network.adapter.LiveDataCallAdapterFactory
import br.com.painelb.network.adapter.SynchronousCallAdapterFactory
import br.com.painelb.network.token.TokenInterceptor
import br.com.painelb.prefs.PreferenceStorage
import br.com.painelb.util.IntToBooleanAdapter
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@Suppress("unused")
class ApiModule {
    @Singleton
    @Provides
    fun provideBaseUrl(): HttpUrl {
        return API_URL
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).add(IntToBooleanAdapter()).build()
    }

    @Singleton
    @Provides
    fun provideTokenInterceptor(storage: PreferenceStorage): TokenInterceptor {
        return TokenInterceptor(storage)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        val mBuilder = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .connectTimeout(10, TimeUnit.MINUTES)
        if (BuildConfig.DEBUG) mBuilder.addNetworkInterceptor(StethoInterceptor())
        mBuilder.addInterceptor(tokenInterceptor)
        return mBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(mBaseUrl: HttpUrl, mClient: OkHttpClient, mMoshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .client(mClient)
            .baseUrl(mBaseUrl)
            .addConverterFactory(MoshiConverterFactory.create(mMoshi))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addCallAdapterFactory(SynchronousCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(mRetrofit: Retrofit): ApiService {
        return mRetrofit.create(ApiService::class.java)
    }

    companion object {
        val API_URL: HttpUrl = HttpUrl.get("http://api.sistran.app.br/api/")
       // val API_URL: HttpUrl = HttpUrl.get("http://192.168.1.104:3000/api/")
    }
}
