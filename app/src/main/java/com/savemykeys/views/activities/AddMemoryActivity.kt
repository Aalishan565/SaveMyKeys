package com.savemykeys.views.activities

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.savemykeys.R
import com.savemykeys.db.entity.Memory
import com.savemykeys.utils.AppUtils
import com.savemykeys.utils.Constants
import com.savemykeys.viewmodel.MemoryViewModel
import kotlinx.android.synthetic.main.activity_add_memory.*
import java.text.SimpleDateFormat
import java.util.*

class AddMemoryActivity : AppCompatActivity() {

    private val myCalendar = Calendar.getInstance()
    private val TAG = "AddMemoryActivity"
    private var screenTitle: String? = null
    private lateinit var memoryViewModel: MemoryViewModel
    private var memory: Memory? = null

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
        memoryViewModel = ViewModelProviders.of(this).get(MemoryViewModel::class.java)
        memory = bundle?.getParcelable(Constants.SINGLE_RECORD)
        if (null != memory) {
            Log.d(TAG, "record not null: $memory")
            etMemoryTitle.setText(memory?.memoryTitle)
            etMemoryDate.setText(memory?.memoryDate)
            etMemoryNote.setText(memory?.memoryNote)
        }
        etMemoryDate.setOnClickListener { v ->
            AppUtils.hideKeyboard(this, v)
            val datePickerDialog = DatePickerDialog(
                this, date, myCalendar.get(
                    Calendar.YEAR
                ),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.show()
        }
        btnSaveMemory.setOnClickListener {
            Log.d(TAG, "btnSave()")
            if (screenTitle.equals(getString(R.string.addMemory), ignoreCase = true)) {
                memoryViewModel.addOrUpdateMemory(
                    etMemoryTitle.text.toString(),
                    etMemoryDate.text.toString(),
                    etMemoryNote.text.toString(),
                    true
                )

            } else {
                memoryViewModel.addOrUpdateMemory(
                    etMemoryTitle.text.toString(),
                    etMemoryDate.text.toString(),
                    etMemoryNote.text.toString(),
                    false,
                    memory!!.memoryId
                )
            }

        }
        memoryViewModel.getMemoryStatus()
            .observe(this,
                Observer<Int> { message -> showRecordStatus(message) }
            )
    }

    var date =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar[Calendar.YEAR] = year
            myCalendar[Calendar.MONTH] = monthOfYear
            myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
            updateUI()
        }

    private fun updateUI() {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        etMemoryDate.setText(sdf.format(myCalendar.time))
    }

    private fun showRecordStatus(message: Int) {
        Log.d(TAG, "showRecordStatus() ${getString(message)}")
        AppUtils.showToastMessageById(this, message)
        if (message == R.string.recordAddedSuccessfully || message == R.string.recordUpdatedSuccessfully) {
            finish()
        }
    }
}
