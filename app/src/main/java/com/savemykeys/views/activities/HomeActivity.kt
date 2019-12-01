package com.savemykeys.views.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.savemykeys.R
import com.savemykeys.application.SaveMyKeysApplication
import com.savemykeys.db.AppDatabase
import com.savemykeys.db.daos.RecordDao
import com.savemykeys.db.entity.Record
import com.savemykeys.utils.Constants
import com.savemykeys.views.adapters.RecordAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private var databaseInstance: AppDatabase? = null
    private var recordDao: RecordDao? = null
    private var recordList: List<Record>? = null
    private var recordAdapter: RecordAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        fabAdd.setOnClickListener { view ->
            var intent = Intent(this, AddRecordActivity::class.java)
            intent.putExtra(
                Constants.SINGLE_RECORD_SCREEN_TITLE, getString(R.string.addRecord)
            )
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.menu_search -> {
            }
            else -> {

            }
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
