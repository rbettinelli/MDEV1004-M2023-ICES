package com.example.mdev1004s23FINAL

// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// 08/23/2023 - RBettinelli - Header and Documentation Added
// -------------------------------------------------------------

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import android.util.Log
import android.widget.ImageButton

class ItemListing : AppCompatActivity(), ItemListApiResponseCallback, ItemDeleteApiResponseCallback {

    //Var
    private val comLib = LibCom()
    private val iodbList = IOdbItemList(this)
    private val iodbDelete = IOdbItemDelete(this)
    private lateinit var itemAdapter: ItemAdapter
    private var btnMAdd: ImageButton? = null
    private var delItemSelected: MyItem = MyItem("", "", "", "", "", "","",emptyList() ,"","","")

    // Main Setup Auth Grab view and buttons.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itemlisting)
        btnMAdd = findViewById(R.id.ibAdd)
        btnMAdd?.setOnClickListener { _ -> addMovie() }

        val auth: String = comLib.sharePRead(applicationContext,"auth").toString()
        val bearerAndAuth = "Bearer $auth"
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        itemAdapter = ItemAdapter(mutableListOf()) { item ->
            // Handle the delete button click here
            deleteItem(item)
        }
        recyclerView.adapter = itemAdapter
        iodbList.getItemList(bearerAndAuth)
    }

    // Delete Movie Button.
    private fun deleteItem(item: MyItem) {
        // Implement your logic to delete the movie
        // Update the dataset and notify adapter if needed
        Log.d("crap","Delete!")
        val auth: String = comLib.sharePRead(applicationContext,"auth").toString()
        val bearerAndAuth = "Bearer $auth"
        delItemSelected = item
        iodbDelete.deleteItem(bearerAndAuth, item._id)
    }

    // Add Function if Add Success / Fails.
    override fun onSuccess(response: ItemResponse) {

        val items = response.data
        itemAdapter.items = items
        itemAdapter.notifyDataSetChanged()

        // Applying OnClickListener to our Adapter
        itemAdapter.setOnClickListener(object :
            ItemAdapter.OnClickListener {
            override fun onClick(position: Int, model: MyItem) {
                val intent = Intent(this@ItemListing, Entry::class.java)
                // Passing the data to the
                // EmployeeDetails Activity
                intent.putExtra("item", model)
                startActivity(intent)
            }
        })
    }

    override fun onFailure(call: Call<ItemResponse>, t: Throwable) {
        // Handle login failure here
        // Show an error message, log the error, etc.
        Log.d("crap","JSON Call Error")
        comLib.showAlert(this, "Error", "Could Not Add Movie.")
    }

    // Add Movie Button.. Jump to Add movie
    private fun addMovie() {
        startActivity(Intent(this@ItemListing, Entry::class.java))
    }


    // Delete Functions if Delete Success / Fails.
    override fun onDSuccess(response: ItemDeleteWrapper) {
        itemAdapter.remove(delItemSelected)
        Log.d("crap","Delete Successful")
    }

    override fun onDFailure(call: Call<ItemDeleteWrapper>, t: Throwable) {
        Log.d("crap","Delete Error")
        comLib.showAlert(this, "Error", "Could Not Delete Movie.")
    }

}
