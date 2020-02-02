package com.savemykeys.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.savemykeys.R
import com.savemykeys.utils.Constants
import kotlinx.android.synthetic.main.activity_add_memory.*

class AddMemoryActivity : AppCompatActivity() {

    private val TAG = "AddMemoryActivity"
    private var screenTitle: String? = null

    override fun onSupportNavigateUp(): Boolean {
        Log.d(TAG, "onSupportNavigateUp()")
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_memory)
        Log.d(TAG, "onCreate()")
        val bundle = intent.extras
        screenTitle = bundle?.getString(Constants.ADD_MEMORY_SCREEN_TITLE)
        toolbarAddMemory.title = screenTitle
        setSupportActionBar(toolbarAddMemory)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
    }
}
