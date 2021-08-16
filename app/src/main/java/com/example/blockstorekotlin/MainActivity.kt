package com.example.blockstorekotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.blockstorekotlin.databinding.ActivityMainBinding
import com.google.android.gms.auth.blockstore.Blockstore
import com.google.android.gms.auth.blockstore.StoreBytesData
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {
    var editStoreBytes: EditText? = null
    var resultRetrieveBytes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        binding.buttonStoreBytes.setOnClickListener { onStoreBytesButtonClick() }
        binding.buttonRetrieveBytes.setOnClickListener { onRetrieveBytesButtonClick() }

        editStoreBytes = binding.editStoreBytes
        resultRetrieveBytes = binding.resultRetrieveBytes

        setContentView(binding.root)
    }

    private fun updateRetrievedText(s: String) {
        val builder = StringBuilder()
        builder.append(getText(R.string.retrieveddata))
            .append(" ")
            .append(s)
        resultRetrieveBytes?.setText(builder.toString())
    }

    @SuppressLint("SetTextI18n")
    fun onStoreBytesButtonClick() {
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

    fun onRetrieveBytesButtonClick() {
        resultRetrieveBytes?.setText(R.string.calling)
        Blockstore.getClient(this)
            .retrieveBytes()
            .addOnSuccessListener { result -> updateRetrievedText(String(result)) }
            .addOnFailureListener { e -> println("Error in retrieving bytes: " + e) }
    }
}