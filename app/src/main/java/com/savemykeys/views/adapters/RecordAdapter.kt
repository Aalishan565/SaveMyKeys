package com.savemykeys.views.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.savemykeys.R
import com.savemykeys.db.entity.Record
import com.savemykeys.views.activities.AddRecordActivity
import com.savemykeys.views.impls.HomePresenterImpl
import com.savemykeys.views.listeners.HomeViewListener
import com.savemykeys.views.presenters.HomePresenter
import com.savemykeys.utils.AppUtils
import com.savemykeys.utils.Constants
import kotlinx.android.synthetic.main.row_items_record.view.*
import com.savemykeys.views.activities.HomeActivity


class RecordAdapter(val context: Context) :
    RecyclerView.Adapter<RecordAdapter.ViewHolder>(), HomeViewListener {
    private var recordList: List<Record> = ArrayList()
    private lateinit var view: View

    private var homePresenter: HomePresenter = HomePresenterImpl(context, this)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = LayoutInflater.from(context).inflate(R.layout.row_items_record, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recordList.size
    }
    fun setDataToList( recordList: List<Record>){
        this.recordList=recordList
        notifyDataSetChanged()

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
            homePresenter.deleteRecord(recordList!![position])
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardRowItem = view.cardRowItem
        val tvUrl = view.tvUrl
        val tvUserName = view.tvUserName
        val ivDelete = view.ivDelete
    }

    override fun successfullyDeleted(message: Int) {
        AppUtils.showSnackBarMessageById(context, view, message)
        (context as HomeActivity).loadData()

    }


}