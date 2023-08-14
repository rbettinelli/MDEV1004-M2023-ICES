package com.example.mdev1004s23FINAL

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

class IOdbLogin(private val loginApiResponseCallback: LoginApiResponseCallback) {

    // "http://192.168.0.111:3000"
    // "https://mdev1004s23rbfinal.onrender.com"
    private val myBaseURL: String = "https://mdev1004s23rbfinal.onrender.com"

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(myBaseURL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun login(parameters: Map<String,String>) {

        val loginService: LoginService = retrofit.create(LoginService::class.java)
        loginService.login(parameters).enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse  != null) {
                        // Notify the callback of successful login
                        loginApiResponseCallback.onSuccess(loginResponse)
                    } else {
                        Log.d("mdev1004s23ice", "RB - Call Error. " + response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    loginApiResponseCallback.onFailure(call, Throwable(errorBody))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Notify the callback of failure
                loginApiResponseCallback.onFailure(call, t)
            }
        })
    }

}

class IOdbRegister(private val registerApiResponseCallback: RegisterApiResponseCallback) {

    // "http://192.168.0.111:3000"
    // "https://mdev1004s23rbfinal.onrender.com"
    private val myBaseURL: String = "https://mdev1004s23rbfinal.onrender.com"

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(myBaseURL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun register(parameters: Map<String,String>) {

        val registerService: RegisterService = retrofit.create(RegisterService::class.java)
        registerService.register(parameters).enqueue(object: Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    if (registerResponse  != null) {
                        // Notify the callback of successful login
                        registerApiResponseCallback.onRSuccess(registerResponse)
                    } else {
                        Log.d("mdev1004s23ice", "RB - Call Error. " + response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    registerApiResponseCallback.onRFailure(call, Throwable(errorBody))
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                // Notify the callback of failure
                registerApiResponseCallback.onRFailure(call, t)
            }
        })
    }

}

class IOdbItemList(private val itemListApiResponseCallback: ItemListApiResponseCallback) {

    private val myBaseURL: String = "https://mdev1004s23rbfinal.onrender.com"

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(myBaseURL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun getItemList( bearerAndAuth: String) {

        val itemService = retrofit.create(ItemService::class.java)
        val call = itemService.getItems(bearerAndAuth)
        call.enqueue(object : Callback<ItemResponse> {
            override fun onResponse(call: Call<ItemResponse>, response: Response<ItemResponse>) {
                if (response.isSuccessful) {
                    val itemResponse = response.body()
                    if (itemResponse  != null) {
                        itemListApiResponseCallback.onSuccess(itemResponse)
                    }else {
                        Log.d("mdev1004s23", "RB - Empty. " + response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    itemListApiResponseCallback.onFailure(call, Throwable(errorBody))
                }
            }
            override fun onFailure(call: Call<ItemResponse>, t: Throwable) {
                // Notify the callback of failure
                itemListApiResponseCallback.onFailure(call, t)
            }
        })
    }

}

class IOdbItemAdd(private val itemAddApiResponseCallback: ItemAddApiResponseCallback) {

    private val myBaseURL: String = "https://mdev1004s23rbfinal.onrender.com"

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(myBaseURL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun addItem(bearerAndAuth: String,parameters: Map<String,String>) {

        val itemAddService = retrofit.create(ItemAddService::class.java)
        val call = itemAddService.addItem(bearerAndAuth, parameters)
        call.enqueue(object : Callback<ItemResponseWrapper> {
            override fun onResponse(call: Call<ItemResponseWrapper>, response: Response<ItemResponseWrapper>) {
                if (response.isSuccessful) {
                    val itemResponse = response.body()
                    if (itemResponse  != null) {
                        itemAddApiResponseCallback.onASuccess(itemResponse)
                    }else {
                        Log.d("mdev1004s23ice", "RB - Empty. " + response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    itemAddApiResponseCallback.onAFailure(call, Throwable(errorBody))
                }
            }
            override fun onFailure(call: Call<ItemResponseWrapper>, t: Throwable) {
                // Notify the callback of failure
                itemAddApiResponseCallback.onAFailure(call, t)
            }
        })
    }

}

class IOdbItemUpdate(private val itemUpdateApiResponseCallback: ItemUpdateApiResponseCallback) {

    private val myBaseURL: String = "https://mdev1004s23rbfinal.onrender.com"

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(myBaseURL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun updateItem(bearerAndAuth: String, id: String, parameters: Map<String,String>) {

        val itemUpdateService = retrofit.create(ItemUpdateService::class.java)
        val call = itemUpdateService.updateItem(id, bearerAndAuth, parameters)
        call.enqueue(object : Callback<ItemResponseWrapper> {
            override fun onResponse(call: Call<ItemResponseWrapper>, response: Response<ItemResponseWrapper>) {
                if (response.isSuccessful) {
                    val itemResponse = response.body()
                    if (itemResponse  != null) {
                        itemUpdateApiResponseCallback.onUSuccess(itemResponse)
                    }else {
                        Log.d("mdev1004s23ice", "RB - Empty. " + response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    itemUpdateApiResponseCallback.onUFailure(call, Throwable(errorBody))
                }
            }
            override fun onFailure(call: Call<ItemResponseWrapper>, t: Throwable) {
                // Notify the callback of failure
                itemUpdateApiResponseCallback.onUFailure(call, t)
            }
        })
    }

}

class IOdbItemDelete(private val itemDeleteApiResponseCallback: ItemDeleteApiResponseCallback) {

    private val myBaseURL: String = "https://mdev1004s23rbfinal.onrender.com"

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(myBaseURL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun deleteItem(bearerAndAuth: String, id: String) {

        val itemDeleteService = retrofit.create(ItemDeleteService::class.java)
        val call = itemDeleteService.deleteItems(id, bearerAndAuth)
        call.enqueue(object : Callback<ItemDeleteWrapper> {
            override fun onResponse(call: Call<ItemDeleteWrapper>, response: Response<ItemDeleteWrapper>) {
                if (response.isSuccessful) {
                    val itemResponse = response.body()
                    if (itemResponse  != null) {
                        itemDeleteApiResponseCallback.onDSuccess(itemResponse)
                    }else {
                        Log.d("mdev1004s23ice", "RB - Empty. " + response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    itemDeleteApiResponseCallback.onDFailure(call, Throwable(errorBody))
                }
            }
            override fun onFailure(call: Call<ItemDeleteWrapper>, t: Throwable) {
                // Notify the callback of failure
                itemDeleteApiResponseCallback.onDFailure(call, t)
            }
        })
    }

}

// Login
interface LoginService {
    @POST("/api/login")
    fun login(@Body parameters: Map<String, String>): Call<LoginResponse>
}

interface LoginApiResponseCallback {
    fun onSuccess(response: LoginResponse)
    fun onFailure(call: Call<LoginResponse>, t: Throwable)
}

// Register
interface RegisterService {
    @POST("/api/register")
    fun register(@Body parameters: Map<String, String>): Call<RegisterResponse>
}

interface RegisterApiResponseCallback {
    fun onRSuccess(response: RegisterResponse)
    fun onRFailure(call: Call<RegisterResponse>, t: Throwable)
}

// Item List
interface ItemService {
    @GET("/api/list")
    fun getItems(@Header("Authorization") authToken: String): Call<ItemResponse>
}

interface ItemListApiResponseCallback {
    fun onSuccess(response: ItemResponse)
    fun onFailure(call: Call<ItemResponse>, t: Throwable)
}

//Item Update
interface ItemUpdateService {
    @PUT("/api/update/{id}")
    fun updateItem(
        @Path("id") id: String,
        @Header("Authorization") authToken: String,
        @Body parameters: Map<String, String>
    ): Call<ItemResponseWrapper>
}

interface ItemUpdateApiResponseCallback {
    fun onUSuccess(response: ItemResponseWrapper)
    fun onUFailure(call: Call<ItemResponseWrapper>, t: Throwable)
}

//Item Add
interface ItemAddService {
    @POST("/api/add")
    fun addItem(
        @Header("Authorization") authToken: String,
        @Body parameters: Map<String, String>
    ): Call<ItemResponseWrapper>
}

interface ItemAddApiResponseCallback {
    fun onASuccess(response: ItemResponseWrapper)
    fun onAFailure(call: Call<ItemResponseWrapper>, t: Throwable)
}

//Item Delete
interface ItemDeleteService {
    @DELETE("/api/delete/{id}")
    fun deleteItems(
        @Path("id") id: String,
        @Header("Authorization") authToken: String
    ): Call<ItemDeleteWrapper>
}

interface ItemDeleteApiResponseCallback {
    fun onDSuccess(response: ItemDeleteWrapper)
    fun onDFailure(call: Call<ItemDeleteWrapper>, t: Throwable)
}

