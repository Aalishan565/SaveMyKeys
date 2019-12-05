package com.savemykeys.views.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.savemykeys.R
import com.savemykeys.db.entity.Record
import com.savemykeys.utils.Constants
import com.savemykeys.views.activities.AddRecordActivity
import com.savemykeys.views.listeners.HomeViewListener
import com.savemykeys.views.listeners.RecordDeleteListener
import kotlinx.android.synthetic.main.row_items_record.view.*


class RecordAdapter(val context: Context, val deleteListener: RecordDeleteListener) :
    RecyclerView.Adapter<RecordAdapter.ViewHolder>(){

    private val TAG = "RecordAdapter"
    private var recordList: List<Record> = ArrayList()
    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        view = LayoutInflater.from(context).inflate(R.layout.row_items_record, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount")
        return recordList.size
    }

    fun setDataToList(recordList: List<Record>) {
        Log.d(TAG, "setDataToList")
        this.recordList = recordList
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")
        holder.tvUrl.text = recordList!![position].url
        holder.tvUserName.text = recordList!![position].userName
        holder.cardRowItem.setOnClickListener {
            var intent = Intent(context, AddRecordActivity::class.java)
            intent.putExtra(Constants.SINGLE_RECORD, recordList!![position])
            intent.putExtra(
                Constants.SINGLE_RECORD_SCREEN_TITLE,
                context.getString(R.string.record)
            )
            context.startActivity(intent)

        }
        holder.ivDelete.setOnClickListener {
            deleteListener.deleteRecord(recordList!![position])
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardRowItem = view.cardRowItem
        val tvUrl = view.tvUrl
        val tvUserName = view.tvUserName
        val ivDelete = view.ivDelete
    }

}