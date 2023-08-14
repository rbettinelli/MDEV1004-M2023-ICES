package com.example.mdev1004s23A4

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult


@Suppress("DEPRECATION")
class Entry : AppCompatActivity(), MovieAddApiResponseCallback, MovieUpdateApiResponseCallback, MoviePaymentApiResponseCallback {

    // Stripe var
    companion object {
        private const val TAG = "CheckoutActivity"
        //private const val BACKEND_URL = "http://192.168.0.111:3000"
    }
    private lateinit var paymentIntentClientSecret: String
    private lateinit var paymentSheet: PaymentSheet

    private val comLib = LibCom()
    private val iodbAdd = IOdbMovieAdd(this)
    private val iodbUpdate = IOdbMovieUpdate(this)
    private val iodbPay = IOdbMoviePayment(this)
    private var btnCan: Button? = null
    private var btnUpdate: Button? = null
    private lateinit var btnBuy: Button
    private var dirty: Boolean = false
    private var pageTitle: TextView? = null
    private var movieUUID: String = ""


    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        btnCan = findViewById(R.id.btnMcan)
        btnCan?.setOnClickListener { _ -> back() }
        btnUpdate = findViewById(R.id.btnMupdate)
        btnUpdate?.setOnClickListener { _ -> writeMovie() }
        pageTitle = findViewById(R.id.txtTitle)
        btnBuy = findViewById(R.id.btnMBuy)
        btnBuy.visibility = View.GONE
        btnBuy.setOnClickListener(::onPayClicked)
        btnBuy.isEnabled = false
        pageTitle?.text = getString(R.string.addnew)
        btnUpdate?.text = getString(R.string.save)


        val intent = intent
        val movie = intent.getSerializableExtra("movie") as? Movie

        if (movie != null) {
            // Supplied By Select
            pageTitle?.text = getString(R.string.edit)
            btnUpdate?.text = getString(R.string.update)
            btnBuy.visibility = View.VISIBLE

            paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)

            val textInputLayoutID: TextInputLayout = findViewById(R.id.mID)
            val editTextID = textInputLayoutID.editText
            editTextID?.setText(movie.movieID.toString())
            
            val textInputLayoutTitle: TextInputLayout = findViewById(R.id.mTitle)
            val editTextTitle = textInputLayoutTitle.editText
            editTextTitle?.setText(movie.title)

            val textInputLayoutStudio: TextInputLayout = findViewById(R.id.mStudio)
            val editTextStudio = textInputLayoutStudio.editText
            editTextStudio?.setText(movie.studio)

            val textInputLayoutGenres: TextInputLayout = findViewById(R.id.mGenres)
            val editTextGenres = textInputLayoutGenres.editText
            editTextGenres?.setText(movie.genres.joinToString(", "))

            val textInputLayoutDirectors: TextInputLayout = findViewById(R.id.mDirectors)
            val editTextDirectors = textInputLayoutDirectors.editText
            editTextDirectors?.setText(movie.directors.joinToString(", "))

            val textInputLayoutWriters: TextInputLayout = findViewById(R.id.mWriters)
            val editTextWriters = textInputLayoutWriters.editText
            editTextWriters?.setText(movie.writers.joinToString(", "))

            val textInputLayoutActors: TextInputLayout = findViewById(R.id.mActors)
            val editTextActors = textInputLayoutActors.editText
            editTextActors?.setText(movie.actors.joinToString(", "))

            val textInputLayoutLength: TextInputLayout = findViewById(R.id.mLength)
            val editTextLength = textInputLayoutLength.editText
            editTextLength?.setText(movie.length)

            val textInputLayoutYear: TextInputLayout = findViewById(R.id.mYear)
            val editTextYear = textInputLayoutYear.editText
            editTextYear?.setText(movie.year.toString())

            val textInputLayoutDesc: TextInputLayout = findViewById(R.id.mDescription)
            val editTextDesc = textInputLayoutDesc.editText
            editTextDesc?.setText(movie.shortDescription)

            val textInputLayoutPosterLink: TextInputLayout = findViewById(R.id.mPosterLink)
            val editTextPosterLink = textInputLayoutPosterLink.editText
            editTextPosterLink?.setText(movie.posterLink)

            val textInputLayoutMpa: TextInputLayout = findViewById(R.id.mMpaRating)
            val editTextMpa = textInputLayoutMpa.editText
            editTextMpa?.setText(movie.mpaRating)

            val textInputLayoutCriticRating: TextInputLayout = findViewById(R.id.mCriticsRating)
            val editTextCriticRating = textInputLayoutCriticRating.editText
            editTextCriticRating?.setText(movie.criticsRating.toString())
            movieUUID = movie._id
            dirty = true
            fetchPaymentIntent()
        }
    }

    private fun fetchPaymentIntent() {
        iodbPay.paymentMovie()
    }

    override fun onPSuccess(response: PaymentWrapper) {
        paymentIntentClientSecret = response.clientSecret
        runOnUiThread { btnBuy.isEnabled = true }
        Log.i(TAG, "Retrieved PaymentIntent")
    }

    override fun onPFailure(call: Call<PaymentWrapper>, t: Throwable) {
        comLib.showAlert(this,"Failed to load page", "Error")
    }

    private fun onPaymentSheetResult(paymentResult: PaymentSheetResult) {
        when (paymentResult) {
            is PaymentSheetResult.Completed -> {
                comLib.showAlert(this,"Payment Complete", "Paid!")
            }
            is PaymentSheetResult.Canceled -> {
                Log.i(TAG, "Payment canceled!")
            }
            is PaymentSheetResult.Failed -> {
                comLib.showAlert(this,"Payment Failed", paymentResult.error.localizedMessage)
            }
        }
    }

    // Test Card : 4242 4242 4242 4242  - 12/34
    private fun onPayClicked(view: View) {

        val myAddress = PaymentSheet.Address.Builder()
            .country("CA")
            .build()

        val billingDetails = PaymentSheet.BillingDetails.Builder()
            .address(myAddress)
            .build()

        val configuration = PaymentSheet.Configuration.Builder("io-serv")
            .defaultBillingDetails(billingDetails)
            .allowsDelayedPaymentMethods(true)
            .build()

        paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, configuration)
    }

    private fun back() {
        finish()
    }

    private fun writeMovie() {

        val auth: String = comLib.sharePRead(applicationContext,"auth").toString()

        val textInputLayoutID: TextInputLayout = findViewById(R.id.mID)
        val editTextID = textInputLayoutID.editText

        val textInputLayoutTitle: TextInputLayout = findViewById(R.id.mTitle)
        val editTextTitle = textInputLayoutTitle.editText

        val textInputLayoutStudio: TextInputLayout = findViewById(R.id.mStudio)
        val editTextStudio = textInputLayoutStudio.editText

        val textInputLayoutGenres: TextInputLayout = findViewById(R.id.mGenres)
        val editTextGenres = textInputLayoutGenres.editText

        val textInputLayoutDirectors: TextInputLayout = findViewById(R.id.mDirectors)
        val editTextDirectors = textInputLayoutDirectors.editText

        val textInputLayoutWriters: TextInputLayout = findViewById(R.id.mWriters)
        val editTextWriters = textInputLayoutWriters.editText

        val textInputLayoutActors: TextInputLayout = findViewById(R.id.mActors)
        val editTextActors = textInputLayoutActors.editText

        val textInputLayoutLength: TextInputLayout = findViewById(R.id.mLength)
        val editTextLength = textInputLayoutLength.editText

        val textInputLayoutYear: TextInputLayout = findViewById(R.id.mYear)
        val editTextYear = textInputLayoutYear.editText

        val textInputLayoutDesc: TextInputLayout = findViewById(R.id.mDescription)
        val editTextDesc = textInputLayoutDesc.editText

        val textInputLayoutPosterLink: TextInputLayout = findViewById(R.id.mPosterLink)
        val editTextPosterLink = textInputLayoutPosterLink.editText

        val textInputLayoutMpa: TextInputLayout = findViewById(R.id.mMpaRating)
        val editTextMpa = textInputLayoutMpa.editText

        val textInputLayoutCriticRating: TextInputLayout = findViewById(R.id.mCriticsRating)
        val editTextCriticRating = textInputLayoutCriticRating.editText

        val movieID: String = editTextID?.text?.toString()?: ""
        val title: String = editTextTitle?.text?.toString() ?: ""
        val studio: String =  editTextStudio?.text?.toString() ?: ""
        val genres: String =  editTextGenres?.text?.toString() ?: ""
        val directors: String =  editTextDirectors?.text?.toString() ?: ""
        val writers: String =  editTextWriters?.text?.toString() ?: ""
        val actors: String =  editTextActors?.text?.toString() ?: ""
        val year: String =  editTextYear?.text?.toString()?:""
        val length: String =  editTextLength?.text?.toString() ?: ""
        val shortDescription: String =  editTextDesc?.text?.toString() ?: ""
        val mpaRating: String =  editTextMpa?.text?.toString() ?: ""
        val criticsRating: String =  editTextCriticRating?.text?.toString()?: ""
        val posterLink: String =  editTextPosterLink?.text?.toString() ?: ""

        val parameters = mapOf(
            "movieID" to movieID,
            "title" to title,
            "studio" to studio,
            "genres" to genres,
            "directors" to directors,
            "writers" to writers,
            "actors" to actors,
            "year" to year,
            "length" to length,
            "shortDescription" to shortDescription,
            "mpaRating" to mpaRating,
            "criticsRating" to criticsRating,
            "posterLink" to posterLink
        )

        val bearerAndAuth = "Bearer $auth"

        if (dirty) {
            //dirty means it has data.
            iodbUpdate.updateMovie( bearerAndAuth, movieUUID, parameters)
        } else {
            //no data .. add.
            iodbAdd.addMovie(bearerAndAuth, parameters)
        }

    }

    override fun onUSuccess(response: MovieResponseWrapper) {
        startActivity(Intent(this@Entry, Movielisting::class.java))
    }

    override fun onUFailure(call: Call<MovieResponseWrapper>, t: Throwable) {
        Log.d("mdev1004s23A4", "RB - Update Fail.", t)
    }

    override fun onASuccess(response: MovieResponseWrapper) {
        startActivity(Intent(this@Entry, Movielisting::class.java))
    }

    override fun onAFailure(call: Call<MovieResponseWrapper>, t: Throwable) {
        Log.d("mdev1004s23A4", "RB - Network Error.", t)
    }


}
