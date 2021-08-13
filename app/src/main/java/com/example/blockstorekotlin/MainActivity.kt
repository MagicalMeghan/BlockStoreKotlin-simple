package com.example.blockstorekotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.blockstore.Blockstore
import com.google.android.gms.auth.blockstore.StoreBytesData
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {
    var editStoreBytes: EditText? = null
    var resultRetrieveBytes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editStoreBytes = findViewById(R.id.edit_store_bytes)
        resultRetrieveBytes = findViewById(R.id.result_retrieve_bytes)
    }

    private fun updateRetrievedText(s: String) {
        resultRetrieveBytes?.setText("Retrieved data: " + s)
    }

    @SuppressLint("SetTextI18n")
    fun onStoreBytesButtonClick(view: View?) {
        val inputString = editStoreBytes!!.text.toString()
        val inputBytes = inputString.toByteArray()
        val data: StoreBytesData = StoreBytesData.Builder().setBytes(inputBytes).build()
        val storeBytesTask: Task<Int> = Blockstore.getClient(this).storeBytes(data)
        storeBytesTask
            .addOnSuccessListener { result ->
                print("Successfully stored bytes: "+ Integer.toString(result))
            }
            .addOnFailureListener { e -> println("Error in storing bytes: " + e) }
    }

    fun onRetrieveBytesButtonClick(view: View?) {
        resultRetrieveBytes?.setText(R.string.calling)
        Blockstore.getClient(this)
            .retrieveBytes()
            .addOnSuccessListener { result -> updateRetrievedText(String(result)) }
            .addOnFailureListener { e -> println("Error in retrieving bytes: " + e) }
    }
}