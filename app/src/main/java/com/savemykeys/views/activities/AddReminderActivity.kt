package com.savemykeys.views.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.savemykeys.R
import com.savemykeys.utils.Constants
import kotlinx.android.synthetic.main.activity_reminder.*

class AddReminderActivity : AppCompatActivity() {

    private val TAG = "AddReminderActivity"
    private var screenTitle: String? = null

    override fun onSupportNavigateUp(): Boolean {
        Log.d(TAG, "onSupportNavigateUp()")
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)
        Log.d(TAG, "onCreate()")
        val bundle = intent.extras
        screenTitle = bundle?.getString(Constants.ADD_REMINDER_SCREEN_TITLE)
        toolbarAddReminder.title = screenTitle
        setSupportActionBar(toolbarAddReminder)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
    }
}
