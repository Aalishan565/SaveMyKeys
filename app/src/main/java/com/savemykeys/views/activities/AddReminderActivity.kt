package com.savemykeys.views.activities

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.savemykeys.R
import com.savemykeys.db.entity.Reminder
import com.savemykeys.receivers.ReminderReceiver
import com.savemykeys.utils.AppUtils
import com.savemykeys.utils.Constants
import com.savemykeys.viewmodel.ReminderViewModel
import kotlinx.android.synthetic.main.activity_add_reminder.*
import java.text.DateFormat
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
        etReminderTime.setOnClickListener { v ->
            AppUtils.hideKeyboard(this, v)
            val timePickerDialog = TimePickerDialog(
                this, time, myCalendar.get(Calendar.HOUR),
                myCalendar.get(Calendar.MINUTE),
                false
            )
            timePickerDialog.show()
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

    var date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        myCalendar[Calendar.YEAR] = year
        myCalendar[Calendar.MONTH] = monthOfYear
        myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        updateUI()
    }

    var time = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        var hour = hourOfDay
        myCalendar[Calendar.MINUTE] = minute
        if (hourOfDay < 12) {
            Log.d("hour", " if $hour")
            myCalendar[Calendar.AM_PM] = 1
        } else {
            myCalendar[Calendar.AM_PM] = 2
            hour -= 12
            Log.d("hour", " else $hour")
        }
        myCalendar[Calendar.HOUR] = hour
        when {
            hour < 10 && minute < 10 -> {
                etReminderTime.setText("0$hour : 0$minute ${if (hourOfDay < 12) "AM" else "PM"}")
            }
            hour < 10 -> {
                etReminderTime.setText("0$hour : $minute ${if (hourOfDay < 12) "AM" else "PM"}")
            }
            minute < 10 -> {
                etReminderTime.setText("$hour : 0$minute ${if (hourOfDay < 12) "AM" else "PM"}")
            }
            else -> {
                etReminderTime.setText("$hour : $minute ${if (hourOfDay < 12) "AM" else "PM"}")
            }
        }

    }

    private fun updateUI() {
        val df_medium_uk: DateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK)
        val df_medium_uk_str: String = df_medium_uk.format(myCalendar.time)
        etReminderDate.setText(df_medium_uk_str)
    }

    private fun showRecordStatus(message: Int) {
        Log.d(TAG, "showRecordStatus() ${getString(message)}")
        AppUtils.showToastMessageById(this, message)
        if (message == R.string.recordAddedSuccessfully || message == R.string.recordUpdatedSuccessfully) {
            AppUtils.showToastMessage(
                this, "Notification set for: "
                        + myCalendar.get(Calendar.YEAR) + "/"
                        + myCalendar.get(Calendar.MONTH) + "/"
                        + myCalendar.get(Calendar.DAY_OF_MONTH) + "/"
                        + myCalendar.get(Calendar.HOUR_OF_DAY) + "/"
                        + myCalendar.get(Calendar.MINUTE) + "/"
            )
            startAlarm()
            finish()
        }
    }

    private fun startAlarm() {
        val alarmManager =
            getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)
        if (myCalendar.before(Calendar.getInstance())) {
            myCalendar.add(Calendar.DATE, 1)
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, myCalendar.timeInMillis, pendingIntent)
    }
}
