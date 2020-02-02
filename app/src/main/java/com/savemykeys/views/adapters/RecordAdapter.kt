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
import com.savemykeys.views.activities.AddKeyActivity
import com.savemykeys.views.listeners.RecordDeleteListener
import kotlinx.android.synthetic.main.row_items_record.view.*


class RecordAdapter(
    private val context: Context,
    private val deleteListener: RecordDeleteListener
) :
    RecyclerView.Adapter<RecordAdapter.ViewHolder>() {

    private val TAG = "RecordAdapter"
    private var recordList: List<Record> = ArrayList()
    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder()")
        view = LayoutInflater.from(context).inflate(R.layout.row_items_record, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount ${recordList.size}")
        return recordList.size
    }

    fun setDataToList(recordList: List<Record>) {
        Log.d(TAG, "setDataToList() $recordList")
        this.recordList = recordList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() position: $position")
        holder.tvUrl.text = recordList[position].url
        holder.tvUserName.text = recordList[position].userName
        holder.cardRowItem.setOnClickListener {
            val intent = Intent(context, AddKeyActivity::class.java)
            intent.putExtra(Constants.SINGLE_RECORD, recordList[position])
            intent.putExtra(
                Constants.ADD_KEY_SCREEN_TITLE,
                context.getString(R.string.record)
            )
            context.startActivity(intent)
        }
        holder.ivDelete.setOnClickListener {
            deleteListener.deleteRecord(recordList[position])
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardRowItem = view.cardRowItem!!
        val tvUrl = view.tvUrl!!
        val tvUserName = view.tvUserName!!
        val ivDelete = view.ivDelete!!
    }
}