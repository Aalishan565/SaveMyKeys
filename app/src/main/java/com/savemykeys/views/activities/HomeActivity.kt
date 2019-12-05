package com.savemykeys.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.savemykeys.R
import com.savemykeys.db.AppDatabase
import com.savemykeys.db.daos.RecordDao
import com.savemykeys.db.entity.Record
import com.savemykeys.utils.Constants
import com.savemykeys.viewmodel.RecordViewModel
import com.savemykeys.views.adapters.RecordAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var recordViewModel: RecordViewModel
    private var recordAdapter: RecordAdapter? = null
    private val TAG ="HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        Log.d(TAG,"onCreate")
        rvHome.layoutManager = LinearLayoutManager(this)
        recordAdapter = RecordAdapter(this)
        rvHome.adapter=recordAdapter
        recordViewModel = ViewModelProviders.of(this).get(RecordViewModel::class.java)
        fabAdd.setOnClickListener {
            var intent = Intent(this, AddRecordActivity::class.java)
            intent.putExtra(
                Constants.SINGLE_RECORD_SCREEN_TITLE, getString(R.string.addRecord)
            )
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG,"onCreateOptionsMenu")
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG,"onOptionsItemSelected")
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
        Log.d(TAG,"onResume")
        loadData()
    }

    fun loadData() {
        Log.d(TAG,"loadData")
        recordViewModel.getAllRecords().observe(this,
            Observer<List<Record>> {
                    t -> recordAdapter!!.setDataToList(t!!)
                //rvHome.adapter=recordAdapter
                Log.d(TAG,""+t.size)
            })


    }


}
