package com.awrcorp.bitmoney_app.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awrcorp.bitmoney_app.R
import com.awrcorp.bitmoney_app.network.ApiClient
import com.awrcorp.bitmoney_app.repository.AppRepository
import com.awrcorp.bitmoney_app.utils.Anicantik
import com.awrcorp.bitmoney_app.vo.Outcome
import kotlinx.android.synthetic.main.list_outcome.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private val historyList = ArrayList<Outcome>()
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_outcome, parent, false)
        return ViewHolder(view)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun getItemCount(): Int = historyList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(historyList[position])
        with(holder.itemView){
            tv_kategori_outcome.text = historyList[position].category
            tv_nama_outcome.text = historyList[position].name
            tv_tanggal_outcome.text = historyList[position].date
            tv_harga_outcome.text = "Rp " + historyList[position].amount.toString()
            icon_kategori.setOnClickListener {
                ApiClient.instance.deleteOutcome(historyList[position].outcomeId).enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        print("uuuuuuuuuuwwwwwwwwwwwwuuuuuuuuuuuuuuuuu")
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
            }
        }

        holder.itemView.setOnClickListener {

            onClickListener?.onCLick(historyList[position])
        }
    }

//    fun refresh()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(history: Outcome) {
            with(itemView) {

            }
        }
    }

    fun setHistoryList(historyLists: List<Outcome>) {
        if (this.historyList.isNotEmpty()) {
            this.historyList.clear()
        }
        this.historyList.addAll(historyLists)
        notifyDataSetChanged()
    }



    interface OnClickListener {
        fun onCLick(outcome: Outcome)
    }
}