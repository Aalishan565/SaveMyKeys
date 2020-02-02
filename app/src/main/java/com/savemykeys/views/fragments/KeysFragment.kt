package com.savemykeys.views.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.savemykeys.R
import com.savemykeys.db.entity.Record
import com.savemykeys.utils.AppUtils
import com.savemykeys.viewmodel.RecordViewModel
import com.savemykeys.views.adapters.RecordAdapter
import com.savemykeys.views.listeners.RecordDeleteListener
import kotlinx.android.synthetic.main.fragment_keys.*

/**
 * A simple [Fragment] subclass.
 */
class KeysFragment : Fragment(), RecordDeleteListener {

    private lateinit var recordViewModel: RecordViewModel
    private var recordAdapter: RecordAdapter? = null
    private val TAG = "KeysFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keys, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rvKeys.layoutManager = LinearLayoutManager(activity)
        recordAdapter = activity?.let { RecordAdapter(it, this) }
        rvKeys.adapter = recordAdapter
        recordViewModel = ViewModelProviders.of(this).get(RecordViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        Log.d(TAG, "loadData()")
        recordViewModel.getAllRecords().observe(this,
            Observer<List<Record>> { t ->
                recordAdapter!!.setDataToList(t!!)
                Log.d(TAG, "" + t.size)
            })

    }

    override fun deleteRecord(record: Record) {
        Log.d(TAG, "deleteRecord() $record")
        recordViewModel.delete(record)
        context?.let {
            AppUtils.showSnackBarMessageById(
                it, frameLayoutKey, R.string.recordDeletedSuccessfully
            )
        }
    }
}
