package com.savemykeys.views.activities

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.savemykeys.R
import com.savemykeys.db.entity.Record
import com.savemykeys.utils.AppUtils
import com.savemykeys.utils.Constants
import com.savemykeys.viewmodel.RecordViewModel
import com.savemykeys.views.listeners.AddRecordViewListener
import kotlinx.android.synthetic.main.activity_add_record.*


class AddRecordActivity : AppCompatActivity(), AddRecordViewListener {

    private val TAG = "AddRecordActivity"
    private lateinit var recordViewModel: RecordViewModel
    private var screenTitle: String? = null
    private var record: Record? = null

    override fun onSupportNavigateUp(): Boolean {
        Log.d(TAG, "onSupportNavigateUp")
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_record)
        Log.d(TAG, "onCreate")
        recordViewModel = ViewModelProviders.of(this).get(RecordViewModel::class.java)
        recordViewModel.setViewListener(this)
        val bundle = intent.extras
        screenTitle = bundle?.getString(Constants.SINGLE_RECORD_SCREEN_TITLE)
        toolbar.title = screenTitle
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        record = bundle?.getParcelable(Constants.SINGLE_RECORD)
        if (null != record) {
            etSiteUrl.setText(record?.url)
            etUserName.setText(record?.userName)
            etPassword.setText(record?.password)
            etNote.setText(record?.note)
        }

        btnSave.setOnClickListener {
            Log.d(TAG, "btnSave")
            if (screenTitle.equals(getString(R.string.addRecord), ignoreCase = true)) {
                recordViewModel.addOrUpdateRecord(
                    etSiteUrl.text.toString(),
                    etUserName.text.toString(),
                    etPassword.text.toString(),
                    etNote.text.toString(), true
                )

            } else {
                recordViewModel.addOrUpdateRecord(
                    etSiteUrl.text.toString(),
                    etUserName.text.toString(),
                    etPassword.text.toString(),
                    etNote.text.toString(), false
                )
            }

        }
        ivPwdVisibility.setOnClickListener {

            if (!ivPwdVisibility.isChecked) {
                etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                etPassword.setSelection(etPassword.text!!.length)
                ivPwdVisibility.setBackgroundResource(R.drawable.ic_visibility_on_black_24dp)
            } else {
                etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                etPassword.setSelection(etPassword.text!!.length)
                ivPwdVisibility.setBackgroundResource(R.drawable.ic_visibility_off_black_24dp)
            }
        }
    }

    override fun addRecordSuccess(message: Int) {
        Log.d(TAG, "addRecordSuccess")
        AppUtils.showToastMessageById(this, message)
        finish()
    }

    override fun showEmptyFieldMessage(message: Int) {
        Log.d(TAG, "showEmptyFieldMessage")
        AppUtils.showToastMessageById(this, message)
    }
}
