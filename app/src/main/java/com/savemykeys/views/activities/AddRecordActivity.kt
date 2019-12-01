package com.savemykeys.views.activities

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import com.savemykeys.R
import com.savemykeys.db.entity.Record
import com.savemykeys.utils.AppUtils
import com.savemykeys.utils.Constants
import com.savemykeys.views.impls.AddRecordPresenterImpl
import com.savemykeys.views.listeners.AddRecordViewListener
import kotlinx.android.synthetic.main.activity_add_record.*





class AddRecordActivity : AppCompatActivity(), AddRecordViewListener {
    private var screenTitle: String? = null
    private var record: Record? = null
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_record)
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
            var addRecordPresenter = AddRecordPresenterImpl(this, this)
            if (screenTitle.equals(getString(R.string.addRecord), ignoreCase = true)) {
                addRecordPresenter.addRecord(
                    etSiteUrl.text.toString(),
                    etUserName.text.toString(),
                    etPassword.text.toString(),
                    etNote.text.toString(), true
                )
            } else {
                addRecordPresenter.addRecord(
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
        AppUtils.showToastMessageById(this, message)
        finish()
    }

    override fun showEmptyFieldMessage(message: Int) {
        AppUtils.showToastMessageById(this, message)
    }
}
