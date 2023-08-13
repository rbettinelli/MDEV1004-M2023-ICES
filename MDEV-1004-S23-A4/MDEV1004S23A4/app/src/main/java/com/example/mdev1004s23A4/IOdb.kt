package com.example.mdev1004s23ice7c

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

    private val myBaseURL: String = "http://192.168.0.111:3000"

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(myBaseURL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun login(parameters: Map<String,String>) {

        val loginService: LoginService = retrofit.create(LoginService::class.java)
        loginService.login(parameters).enqueue(object : Callback<LoginResponse> {
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

class IOdbMovieList(private val movieListApiResponseCallback: MovieListApiResponseCallback) {

    private val myBaseURL: String = "http://192.168.0.111:3000"

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(myBaseURL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun getMovieList( bearerAndAuth: String) {

        val movieService = retrofit.create(MovieService::class.java)
        val call = movieService.getMovies(bearerAndAuth)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    if (movieResponse  != null) {
                        movieListApiResponseCallback.onSuccess(movieResponse)
                    }else {
                        Log.d("mdev1004s23ice", "RB - Empty. " + response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    movieListApiResponseCallback.onFailure(call, Throwable(errorBody))
                }
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                // Notify the callback of failure
                movieListApiResponseCallback.onFailure(call, t)
            }
        })
    }

}

class IOdbMovieAdd(private val movieAddApiResponseCallback: MovieAddApiResponseCallback) {

    private val myBaseURL: String = "http://192.168.0.111:3000"

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(myBaseURL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun addMovie(bearerAndAuth: String,parameters: Map<String,String>) {

        val movieAddService = retrofit.create(MovieAddService::class.java)
        val call = movieAddService.addMovie(bearerAndAuth, parameters)
        call.enqueue(object : Callback<MovieResponseWrapper> {
            override fun onResponse(call: Call<MovieResponseWrapper>, response: Response<MovieResponseWrapper>) {
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    if (movieResponse  != null) {
                        movieAddApiResponseCallback.onASuccess(movieResponse)
                    }else {
                        Log.d("mdev1004s23ice", "RB - Empty. " + response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    movieAddApiResponseCallback.onAFailure(call, Throwable(errorBody))
                }
            }
            override fun onFailure(call: Call<MovieResponseWrapper>, t: Throwable) {
                // Notify the callback of failure
                movieAddApiResponseCallback.onAFailure(call, t)
            }
        })
    }

}

class IOdbMovieUpdate(private val movieUpdateApiResponseCallback: MovieUpdateApiResponseCallback) {

    private val myBaseURL: String = "http://192.168.0.111:3000"

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(myBaseURL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun updateMovie(bearerAndAuth: String, id: String, parameters: Map<String,String>) {

        val movieUpdateService = retrofit.create(MovieUpdateService::class.java)
        val call = movieUpdateService.updateMovie(id, bearerAndAuth, parameters)
        call.enqueue(object : Callback<MovieResponseWrapper> {
            override fun onResponse(call: Call<MovieResponseWrapper>, response: Response<MovieResponseWrapper>) {
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    if (movieResponse  != null) {
                        movieUpdateApiResponseCallback.onUSuccess(movieResponse)
                    }else {
                        Log.d("mdev1004s23ice", "RB - Empty. " + response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    movieUpdateApiResponseCallback.onUFailure(call, Throwable(errorBody))
                }
            }
            override fun onFailure(call: Call<MovieResponseWrapper>, t: Throwable) {
                // Notify the callback of failure
                movieUpdateApiResponseCallback.onUFailure(call, t)
            }
        })
    }

}

class IOdbMovieDelete(private val movieDeleteApiResponseCallback: MovieDeleteApiResponseCallback) {

    private val myBaseURL: String = "http://192.168.0.111:3000"

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(myBaseURL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun deleteMovie(bearerAndAuth: String, id: String) {

        val movieDeleteService = retrofit.create(MovieDeleteService::class.java)
        val call = movieDeleteService.deleteMovies(id, bearerAndAuth)
        call.enqueue(object : Callback<MovieDeleteWrapper> {
            override fun onResponse(call: Call<MovieDeleteWrapper>, response: Response<MovieDeleteWrapper>) {
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    if (movieResponse  != null) {
                        movieDeleteApiResponseCallback.onDSuccess(movieResponse)
                    }else {
                        Log.d("mdev1004s23ice", "RB - Empty. " + response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    movieDeleteApiResponseCallback.onDFailure(call, Throwable(errorBody))
                }
            }
            override fun onFailure(call: Call<MovieDeleteWrapper>, t: Throwable) {
                // Notify the callback of failure
                movieDeleteApiResponseCallback.onDFailure(call, t)
            }
        })
    }

}

class IOdbMoviePayment(private val moviePaymentApiResponseCallback: MoviePaymentApiResponseCallback) {

    private val myBaseURL: String = "http://192.168.0.111:3000"

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(myBaseURL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun paymentMovie() {

        val moviePaymentService = retrofit.create(MoviePaymentService::class.java)
        val call = moviePaymentService.payMovie()
        call.enqueue(object : Callback<PaymentWrapper> {
            override fun onResponse(call: Call<PaymentWrapper>, response: Response<PaymentWrapper>) {
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    if (movieResponse  != null) {
                        moviePaymentApiResponseCallback.onPSuccess(movieResponse)
                    }else {
                        Log.d("mdev1004s23ice", "RB - Empty. " + response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    moviePaymentApiResponseCallback.onPFailure(call, Throwable(errorBody))
                }
            }
            override fun onFailure(call: Call<PaymentWrapper>, t: Throwable) {
                // Notify the callback of failure
                moviePaymentApiResponseCallback.onPFailure(call, t)
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

// Movie List
interface MovieService {
    @GET("/api/list")
    fun getMovies(@Header("Authorization") authToken: String): Call<MovieResponse>
}

interface MovieListApiResponseCallback {
    fun onSuccess(response: MovieResponse)
    fun onFailure(call: Call<MovieResponse>, t: Throwable)
}

//Movie Update
interface MovieUpdateService {
    @PUT("/api/update/{id}")
    fun updateMovie(
        @Path("id") id: String,
        @Header("Authorization") authToken: String,
        @Body parameters: Map<String, String>
    ): Call<MovieResponseWrapper>
}

interface MovieUpdateApiResponseCallback {
    fun onUSuccess(response: MovieResponseWrapper)
    fun onUFailure(call: Call<MovieResponseWrapper>, t: Throwable)
}

//Movie Payment
interface MoviePaymentService {
    @POST("/api/payment")
    fun payMovie(): Call<PaymentWrapper>
}

interface MoviePaymentApiResponseCallback {
    fun onPSuccess(response: PaymentWrapper)
    fun onPFailure(call: Call<PaymentWrapper>, t: Throwable)
}


//Movie Add
interface MovieAddService {
    @POST("/api/add")
    fun addMovie(
        @Header("Authorization") authToken: String,
        @Body parameters: Map<String, String>
    ): Call<MovieResponseWrapper>
}

interface MovieAddApiResponseCallback {
    fun onASuccess(response: MovieResponseWrapper)
    fun onAFailure(call: Call<MovieResponseWrapper>, t: Throwable)
}

//Movie Delete
interface MovieDeleteService {
    @DELETE("/api/delete/{id}")
    fun deleteMovies(
        @Path("id") id: String,
        @Header("Authorization") authToken: String
    ): Call<MovieDeleteWrapper>
}

interface MovieDeleteApiResponseCallback {
    fun onDSuccess(response: MovieDeleteWrapper)
    fun onDFailure(call: Call<MovieDeleteWrapper>, t: Throwable)
}

