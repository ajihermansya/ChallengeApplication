package com.takehomechallenge.mohamadajihermansya.data.network.apiservice
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClientTest {

    @Test
    fun testApiClientConfiguration() {
        val apiClient = ApiClient


        val okhttp = apiClient.okhttp
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        assertEquals(TimeUnit.SECONDS.toMillis(25).toInt(), okhttp.readTimeoutMillis)
        assertEquals(TimeUnit.SECONDS.toMillis(300).toInt(), okhttp.writeTimeoutMillis)
        assertEquals(TimeUnit.SECONDS.toMillis(60).toInt(), okhttp.connectTimeoutMillis)
        assert(okhttp.interceptors.contains(loggingInterceptor))

        val retrofit = apiClient.retrofit
        assertEquals("https://example.com/", retrofit.baseUrl().toString())
        assertEquals(okhttp, (retrofit.callFactory() as OkHttpClient).newBuilder().build())
        assert(retrofit.converterFactories().any { it is GsonConverterFactory })

    }

    @Test
    fun testApiServiceCreation() {
        val apiClient = ApiClient

        val apiService = apiClient.apiservice
        assertNotNull(apiService)
    }
}
