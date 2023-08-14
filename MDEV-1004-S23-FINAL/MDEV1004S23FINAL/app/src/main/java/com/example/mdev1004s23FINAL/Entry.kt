package com.example.mdev1004s23FINAL

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call

@Suppress("DEPRECATION")
class Entry : AppCompatActivity(), ItemAddApiResponseCallback, ItemUpdateApiResponseCallback {


    private val comLib = LibCom()
    private val iodbAdd = IOdbItemAdd(this)
    private val iodbUpdate = IOdbItemUpdate(this)
    private var btnCan: Button? = null
    private var btnUpdate: Button? = null
    private var dirty: Boolean = false
    private var pageTitle: TextView? = null
    private var itemUUID: String = ""

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        btnCan = findViewById(R.id.btnMcan)
        btnCan?.setOnClickListener { _ -> back() }
        btnUpdate = findViewById(R.id.btnMupdate)
        btnUpdate?.setOnClickListener { _ -> writeItem() }
        pageTitle = findViewById(R.id.txtTitle)
        pageTitle?.text = getString(R.string.addnew)
        btnUpdate?.text = getString(R.string.save)

        val intent = intent
        val item = intent.getSerializableExtra("item") as? MyItem

        if (item != null) {
            // Supplied By Select
            pageTitle?.text = getString(R.string.edit)
            btnUpdate?.text = getString(R.string.update)

            val textInputLayoutID: TextInputLayout = findViewById(R.id.mName)
            val editTextName = textInputLayoutID.editText
            editTextName?.setText(item.name)
            
            val textInputLayoutType: TextInputLayout = findViewById(R.id.mType)
            val editTextType = textInputLayoutType.editText
            editTextType?.setText(item.type)

            val textInputLayoutDateBuilt: TextInputLayout = findViewById(R.id.mDateBuilt)
            val editTextDateBuilt = textInputLayoutDateBuilt.editText
            editTextDateBuilt?.setText(item.dateBuilt)

            val textInputLayoutCity: TextInputLayout = findViewById(R.id.mCity)
            val editTextCity = textInputLayoutCity.editText
            editTextCity?.setText(item.city)

            val textInputLayoutCountry: TextInputLayout = findViewById(R.id.mCountry)
            val editTextCountry = textInputLayoutCountry.editText
            editTextCountry?.setText(item.country)

            val textInputLayoutDesc: TextInputLayout = findViewById(R.id.mDesc)
            val editTextDesc = textInputLayoutDesc.editText
            editTextDesc?.setText(item.description)

            val textInputLayoutArchitects: TextInputLayout = findViewById(R.id.mArchitects)
            val editTextArchitects = textInputLayoutArchitects.editText
            editTextArchitects?.setText(item.architects.joinToString(", "))

            val textInputLayoutCost: TextInputLayout = findViewById(R.id.mCost)
            val editTextCost = textInputLayoutCost.editText
            editTextCost?.setText(item.cost)

            val textInputLayoutWebsite: TextInputLayout = findViewById(R.id.mWebsite)
            val editTextWebsite = textInputLayoutWebsite.editText
            editTextWebsite?.setText(item.website)

            val textInputLayoutImageURL: TextInputLayout = findViewById(R.id.mImageURL)
            val editTextImageURL = textInputLayoutImageURL.editText
            editTextImageURL?.setText(item.imageURL)

            itemUUID = item._id
            dirty = true
        }
    }

    private fun back() {
        finish()
    }

    private fun writeItem() {

        val auth: String = comLib.sharePRead(applicationContext,"auth").toString()

        val textInputLayoutName: TextInputLayout = findViewById(R.id.mName)
        val editTextName = textInputLayoutName.editText

        val textInputLayoutType: TextInputLayout = findViewById(R.id.mType)
        val editTextType = textInputLayoutType.editText

        val textInputLayoutDateBuilt: TextInputLayout = findViewById(R.id.mDateBuilt)
        val editTextDateBuilt = textInputLayoutDateBuilt.editText

        val textInputLayoutCity: TextInputLayout = findViewById(R.id.mCity)
        val editTextCity = textInputLayoutCity.editText

        val textInputLayoutCountry: TextInputLayout = findViewById(R.id.mCountry)
        val editTextCountry = textInputLayoutCountry.editText

        val textInputLayoutDesc: TextInputLayout = findViewById(R.id.mDesc)
        val editTextDesc = textInputLayoutDesc.editText

        val textInputLayoutArchitects: TextInputLayout = findViewById(R.id.mArchitects)
        val editTextArchitects = textInputLayoutArchitects.editText

        val textInputLayoutCost: TextInputLayout = findViewById(R.id.mCost)
        val editTextCost = textInputLayoutCost.editText

        val textInputLayoutWebsite: TextInputLayout = findViewById(R.id.mWebsite)
        val editTextWebsite = textInputLayoutWebsite.editText

        val textInputLayoutImageURL: TextInputLayout = findViewById(R.id.mImageURL)
        val editTextImageURL = textInputLayoutImageURL.editText

        val name: String = editTextName?.text?.toString()?: ""
        val type: String = editTextType?.text?.toString() ?: ""
        val dateBuilt: String =  editTextDateBuilt?.text?.toString() ?: ""
        val city: String =  editTextCity?.text?.toString() ?: ""
        val country: String =  editTextCountry?.text?.toString() ?: ""
        val description: String =  editTextDesc?.text?.toString() ?: ""
        val architects: String =  editTextArchitects?.text?.toString() ?: ""
        val cost: String =  editTextCost?.text?.toString() ?: ""
        val website: String =  editTextWebsite?.text?.toString() ?: ""
        val imageURL: String =  editTextImageURL?.text?.toString() ?: ""

        val parameters = mapOf(
            "name" to name,
            "type" to type,
            "dateBuilt" to dateBuilt,
            "city" to city,
            "country" to country,
            "description" to description,
            "architects" to architects,
            "cost" to cost,
            "website" to website,
            "imageURL" to imageURL
        )

        val bearerAndAuth = "Bearer $auth"

        if (dirty) {
            //dirty means it has data.
            iodbUpdate.updateItem( bearerAndAuth, itemUUID, parameters)
        } else {
            //no data .. add.
            iodbAdd.addItem(bearerAndAuth, parameters)
        }

    }

    override fun onUSuccess(response: ItemResponseWrapper) {
        startActivity(Intent(this@Entry, ItemListing::class.java))
    }

    override fun onUFailure(call: Call<ItemResponseWrapper>, t: Throwable) {
        Log.d("mdev1004s23A4", "RB - Update Fail.", t)
    }

    override fun onASuccess(response: ItemResponseWrapper) {
        startActivity(Intent(this@Entry, ItemListing::class.java))
    }

    override fun onAFailure(call: Call<ItemResponseWrapper>, t: Throwable) {
        Log.d("mdev1004s23A4", "RB - Network Error.", t)
    }

}
