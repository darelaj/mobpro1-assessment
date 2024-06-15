package org.d3if3060.assessment1.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if3060.assessment1.model.OpStatus
import org.d3if3060.assessment1.model.Sepatu
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://unspoken.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface SepatuApiService {
    @GET("api_darel.php")
    suspend fun getSepatu(
        @Header("Authorization") userId: String
    ): List<Sepatu>

    @Multipart
    @POST("api_darel.php")
    suspend fun postSepatu(
        @Header("Authorization") userId: String,
        @Part("merk") merk: RequestBody,
        @Part("ukuran") ukuran: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @DELETE("api_darel.php")
    suspend fun deleteSepatu(
        @Header("Authorization") userId: String,
        @Query("id") id: String
    ): OpStatus

}

object SepatuApi {
    val service: SepatuApiService by lazy {
        retrofit.create(SepatuApiService::class.java)
    }

    fun getPictureUrl(imageId: String): String {
        return "${BASE_URL}image.php?id=$imageId"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }