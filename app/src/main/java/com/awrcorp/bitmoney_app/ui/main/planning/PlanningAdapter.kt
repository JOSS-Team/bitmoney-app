package com.awrcorp.bitmoney_app.ui.main.planning

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awrcorp.bitmoney_app.R
import com.awrcorp.bitmoney_app.network.ApiClient
import com.awrcorp.bitmoney_app.ui.main.home.HomeAdapter
import com.awrcorp.bitmoney_app.vo.Outcome
import kotlinx.android.synthetic.main.list_outcome.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlanningAdapter : RecyclerView.Adapter<PlanningAdapter.ViewHolder>() {
    private val planList = ArrayList<Outcome>()
    private var onClickListener: PlanningAdapter.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_outcome, parent, false)
        return ViewHolder(view)
    }

    fun setOnClickListener(onClickListener: PlanningAdapter.OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun getItemCount(): Int = planList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView){
            tv_kategori_outcome.text = planList[position].category
            tv_nama_outcome.text = planList[position].name
            tv_harga_outcome.text = "Rp " + planList[position].amount.toString()
            tv_tanggal_outcome.visibility = View.GONE
            icon_kategori.setOnClickListener {
                onClickListener?.onCLick(planList[position])
            }
//            icon_kategori.setOnClickListener {
//                ApiClient.instance.deleteOutcome(planList[position].outcomeId).enqueue(object : Callback<Unit> {
//                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
//                        print("uuuuuuuuuuwwwwwwwwwwwwuuuuuuuuuuuuuuuuu")
//                    }
//
//                    override fun onFailure(call: Call<Unit>, t: Throwable) {
//                        t.printStackTrace()
//                    }
//                })
//            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        fun bind(plan: Outcome) {
//            with(itemView) {
//                tv_kategori_outcome.text = plan.category
//                tv_nama_outcome.text = plan.name
//                tv_harga_outcome.text = "Rp " + plan.amount.toString()
//                tv_tanggal_outcome.visibility = View.GONE
//            }
//        }
    }

    fun setPlanList(planLists: List<Outcome>) {
        if (this.planList.isNotEmpty()) {
            this.planList.clear()
        }
        this.planList.addAll(planLists)
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onCLick(outcome: Outcome)
    }
}