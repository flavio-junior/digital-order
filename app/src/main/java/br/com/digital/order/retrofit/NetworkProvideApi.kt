package br.com.digital.order.retrofit

import br.com.digital.order.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor()
        .setLevel(level = HttpLoggingInterceptor.Level.BODY)
}

fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL_APP)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
}
