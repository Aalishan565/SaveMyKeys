package com.savemykeys.views.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.savemykeys.R
import com.savemykeys.db.entity.Reminder
import com.savemykeys.utils.AppUtils
import com.savemykeys.utils.Constants
import com.savemykeys.viewmodel.ReminderViewModel
import kotlinx.android.synthetic.main.activity_add_reminder.*
import java.text.SimpleDateFormat
import java.util.*

class AddReminderActivity : AppCompatActivity() {

    private lateinit var reminderViewModel: ReminderViewModel
    private var reminder: Reminder? = null
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
        screenTitle = bundle?.getString(Constants.ADD_REMINDER_SCREEN_TITLE)
        toolbarAddReminder.title = screenTitle
        setSupportActionBar(toolbarAddReminder)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        reminderViewModel = ViewModelProviders.of(this).get(ReminderViewModel::class.java)
        reminder = bundle?.getParcelable(Constants.SINGLE_RECORD)
        if (null != reminder) {
            Log.d(TAG, "record not null: $reminder")
            etReminderTitle.setText(reminder?.reminderTitle)
            etReminderDate.setText(reminder?.reminderDate)
            etReminderNote.setText(reminder?.reminderNote)
        }
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
        btnSaveMemory.setOnClickListener {
            Log.d(TAG, "btnSave()")
            if (screenTitle.equals(getString(R.string.addReminder), ignoreCase = true)) {
                reminderViewModel.addOrUpdateReminder(
                    etReminderTitle.text.toString(),
                    etReminderDate.text.toString(),
                    etReminderNote.text.toString(),
                    true
                )

            } else {
                reminderViewModel.addOrUpdateReminder(
                    etReminderTitle.text.toString(),
                    etReminderDate.text.toString(),
                    etReminderNote.text.toString(),
                    false, reminder!!.reminderId


                )
            }

        }
        reminderViewModel.getReminderStatus()
            .observe(this,
                Observer<Int> { message -> showRecordStatus(message) }
            )
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

    private fun showRecordStatus(message: Int) {
        Log.d(TAG, "showRecordStatus() ${getString(message)}")
        AppUtils.showToastMessageById(this, message)
        if (message == R.string.recordAddedSuccessfully || message == R.string.recordUpdatedSuccessfully) {
            finish()
        }
    }
}
