package com.savemykeys.views.activities

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.savemykeys.R
import com.savemykeys.db.entity.Key
import com.savemykeys.utils.AppUtils
import com.savemykeys.utils.Constants
import com.savemykeys.viewmodel.KeyViewModel
import kotlinx.android.synthetic.main.activity_add_key.*

class AddKeyActivity : AppCompatActivity() {

    private val TAG = "AddRecordActivity"
    private var screenTitle: String? = null
    private lateinit var keyViewModel: KeyViewModel
    private var key: Key? = null

    override fun onSupportNavigateUp(): Boolean {
        Log.d(TAG, "onSupportNavigateUp()")
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_key)
        Log.d(TAG, "onCreate()")
        val bundle = intent.extras
        screenTitle = bundle?.getString(Constants.ADD_KEY_SCREEN_TITLE)
        toolbarAddKey.title = screenTitle
        setSupportActionBar(toolbarAddKey)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        ivPwdVisibility.isChecked = true
        keyViewModel = ViewModelProviders.of(this).get(KeyViewModel::class.java)
        key = bundle?.getParcelable(Constants.SINGLE_RECORD)
        if (null != key) {
            Log.d(TAG, "record not null: $key")
            etSiteUrl.setText(key?.url)
            etUserName.setText(key?.userName)
            etPassword.setText(key?.password)
            etNote.setText(key?.note)
        }

        btnSave.setOnClickListener {
            Log.d(TAG, "btnSave()")
            if (screenTitle.equals(getString(R.string.addKey), ignoreCase = true)) {
                keyViewModel.addOrUpdateKey(
                    etSiteUrl.text.toString(),
                    etUserName.text.toString(),
                    etPassword.text.toString(),
                    etNote.text.toString(), true
                )

            } else {
                keyViewModel.addOrUpdateKey(
                    etSiteUrl.text.toString(),
                    etUserName.text.toString(),
                    etPassword.text.toString(),
                    etNote.text.toString(), false,
                    key!!.recordId
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
        keyViewModel.getKeyStatus()
            .observe(this,
                Observer<Int> { message -> showRecordStatus(message) }
            )
    }

    private fun showRecordStatus(message: Int) {
        Log.d(TAG, "showRecordStatus() ${getString(message)}")
        AppUtils.showToastMessageById(this, message)
        if (message == R.string.recordAddedSuccessfully || message == R.string.recordUpdatedSuccessfully) {
            finish()
        }
    }
}
