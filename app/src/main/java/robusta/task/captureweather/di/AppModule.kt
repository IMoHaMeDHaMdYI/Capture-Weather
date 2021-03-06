package robusta.task.captureweather.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import robusta.task.captureweather.common.Repo
import robusta.task.captureweather.common.utils.FileHelper
import robusta.task.captureweather.history.HistoryViewModel
import robusta.task.captureweather.image.ImageViewModel
import robusta.task.captureweather.weather.data.WeatherService
import java.util.concurrent.TimeUnit

val appModule = module {
    single {
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }
    single { createOkHttpClient() }
    single<WeatherService> {
        createWebService(get(), get(), getProperty("base_url"))
    }

    factory { FileHelper(androidContext()) }
    factory { Repo(get()) }

    viewModel { (reload: Boolean) -> ImageViewModel(get(), get(), reload) }
    viewModel { HistoryViewModel(get()) }
}


fun createOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .addInterceptor(logging)
        .build()
}


inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    moshi: Moshi,
    url: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    return retrofit.create(T::class.java)
}