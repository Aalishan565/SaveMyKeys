package com.savemykeys.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.savemykeys.R
import com.savemykeys.db.AppDatabase
import com.savemykeys.db.daos.RecordDao
import com.savemykeys.db.entity.Record
import com.savemykeys.views.adapters.RecordAdapter
import com.savemykeys.application.SaveMyKeysApplication
import com.savemykeys.utils.Constants
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private var databaseInstance: AppDatabase? = null
    private var recordDao: RecordDao? = null
    private var recordList: List<Record>? = null
    private var recordAdapter: RecordAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        fabAdd.setOnClickListener { view ->
            var intent = Intent(this, AddRecordActivity::class.java)
            intent.putExtra(
                Constants.SINGLE_RECORD_SCREEN_TITLE, getString(R.string.addRecord)
            )
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

     fun loadData() {
        databaseInstance = (applicationContext as SaveMyKeysApplication).getDatabaseInstance();
        recordDao = databaseInstance?.recordDao()
        recordList = recordDao?.getRecords()
        recordAdapter = recordList?.let { RecordAdapter(this, it) }
        rvHome.layoutManager = LinearLayoutManager(this)
        rvHome.adapter = recordAdapter
    }
}
