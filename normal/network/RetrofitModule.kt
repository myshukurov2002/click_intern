package normal.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitModule {
    private val client: OkHttpClient = OkHttpClient.Builder().build()

    val api: ApiService = Retrofit.Builder()
        .baseUrl("https://fakestoreapi.com/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}
