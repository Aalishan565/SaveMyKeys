package com.savemykeys.views.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.savemykeys.R
import com.savemykeys.db.entity.Reminder
import com.savemykeys.utils.Constants
import com.savemykeys.views.activities.AddMemoryActivity
import com.savemykeys.views.listeners.RecordDeleteListener
import kotlinx.android.synthetic.main.row_items_key.view.cardRowItem
import kotlinx.android.synthetic.main.row_items_key.view.ivDelete
import kotlinx.android.synthetic.main.row_items_memory_reminder.view.*


class ReminderAdapter(
    private val context: Context,
    private val deleteListener: RecordDeleteListener
) :
    RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {

    private val TAG = "ReminderAdapter"
    private var reminderList: List<Reminder> = ArrayList()
    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder()")
        view =
            LayoutInflater.from(context).inflate(R.layout.row_items_memory_reminder, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount ${reminderList.size}")
        return reminderList.size
    }

    fun setDataToList(reminderList: List<Reminder>) {
        Log.d(TAG, "setDataToList() $reminderList")
        this.reminderList = reminderList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() position: $position")
        holder.tvTitle.text = reminderList[position].reminderTitle
        holder.tvDate.text = reminderList[position].reminderDate
        holder.cardRowItem.setOnClickListener {
            val intent = Intent(context, AddMemoryActivity::class.java)
            intent.putExtra(Constants.SINGLE_RECORD, reminderList[position])
            intent.putExtra(
                Constants.ADD_KEY_SCREEN_TITLE,
                context.getString(R.string.record)
            )
            context.startActivity(intent)
        }
        holder.ivDelete.setOnClickListener {
            deleteListener.deleteKey(reminderList[position])
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardRowItem = view.cardRowItem!!
        val tvTitle = view.tvTitle!!
        val tvDate = view.tvDate!!
        val ivDelete = view.ivDelete!!
    }
}