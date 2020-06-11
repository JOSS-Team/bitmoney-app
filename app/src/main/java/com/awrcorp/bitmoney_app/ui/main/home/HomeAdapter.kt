package com.awrcorp.bitmoney_app.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awrcorp.bitmoney_app.R
import com.awrcorp.bitmoney_app.vo.Outcome
import kotlinx.android.synthetic.main.list_outcome.view.*

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private val historyList = ArrayList<Outcome>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_outcome, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = historyList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(historyList[position])
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(history: Outcome) {
            with(itemView) {
                tv_kategori_outcome.text = history.category
                tv_nama_outcome.text = history.name
                tv_tanggal_outcome.text = history.date
                tv_harga_outcome.text = "Rp " + history.amount.toString()
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
}