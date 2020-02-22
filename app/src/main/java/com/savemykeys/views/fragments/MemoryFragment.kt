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
import com.savemykeys.db.entity.Memory
import com.savemykeys.utils.AppUtils
import com.savemykeys.viewmodel.MemoryViewModel
import com.savemykeys.views.adapters.MemoryAdapter
import com.savemykeys.views.listeners.RecordDeleteListener
import kotlinx.android.synthetic.main.fragment_memory.*

/**
 * A simple [Fragment] subclass.
 */
class MemoryFragment : Fragment(), RecordDeleteListener {

    private lateinit var memoryViewModel: MemoryViewModel
    private var memoryAdapter: MemoryAdapter? = null
    private val TAG = "MemoryFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memory, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rvMemory.layoutManager = LinearLayoutManager(activity)
        memoryAdapter = activity?.let { MemoryAdapter(it, this) }
        rvMemory.adapter = memoryAdapter
        memoryViewModel = ViewModelProviders.of(this).get(MemoryViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        Log.d(TAG, "loadData()")
        memoryViewModel.getAllMemory().observe(this,
            Observer<List<Memory>> { t ->
                memoryAdapter!!.setDataToList(t!!)
                Log.d(TAG, "" + t.size)
            })

    }

    override fun deleteKey(memory: Memory) {
        Log.d(TAG, "deleteRecord() $memory")
        memoryViewModel.deleteMemory(memory)
        context?.let {
            AppUtils.showSnackBarMessageById(
                it, frameLayoutMemory, R.string.recordDeletedSuccessfully
            )
        }
    }

}
