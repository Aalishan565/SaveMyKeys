package com.savemykeys.views.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.savemykeys.R
import com.savemykeys.utils.AppUtils
import com.savemykeys.utils.Constants
import kotlinx.android.synthetic.main.activity_add_reminder.*
import java.text.SimpleDateFormat
import java.util.*

class AddReminderActivity : AppCompatActivity() {
    private val myCalendar = Calendar.getInstance()
    private val TAG = "AddReminderActivity"
    private var screenTitle: String? = null

    override fun onSupportNavigateUp(): Boolean {
        Log.d(TAG, "onSupportNavigateUp()")
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)
        Log.d(TAG, "onCreate()")
        val bundle = intent.extras
        screenTitle = bundle?.getString(Constants.ADD_MEMORY_SCREEN_TITLE)
        toolbarAddReminder.title = screenTitle
        setSupportActionBar(toolbarAddReminder)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        etReminderDate.setOnClickListener { v ->
            AppUtils.hideKeyboard(this, v)
            val datePickerDialog = DatePickerDialog(
                this, date, myCalendar.get(
                    Calendar.YEAR
                ),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }
    }

    var date =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar[Calendar.YEAR] = year
            myCalendar[Calendar.MONTH] = monthOfYear
            myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
            updateUI()
        }

    private fun updateUI() {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        etReminderDate.setText(sdf.format(myCalendar.time))
    }
}
