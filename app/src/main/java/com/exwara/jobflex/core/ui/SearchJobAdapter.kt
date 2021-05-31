package com.exwara.jobflex.core.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exwara.jobflex.R
import com.exwara.jobflex.core.domain.model.SearchItem
import com.exwara.jobflex.databinding.ItemRowResultJobBinding
import com.exwara.jobflex.ui.detail.DetailActivity
import java.util.*

class SearchJobAdapter: RecyclerView.Adapter<SearchJobAdapter.ListViewHolder>() {
    private var listData = ArrayList<SearchItem>()

    fun setData(newListData: List<SearchItem>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowResultJobBinding.bind(itemView)
        fun bind(data: SearchItem) {
            with(binding) {
                tvItemTitle.text = data.title
                tvItemCity.text = data.city
                tvItemEnddate.text = data.endDate

                itemView.setOnClickListener{
                    // pindahkan ke detail sambil bawa data nya
                    val intent = Intent(root.context, DetailActivity::class.java)
//                    intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
                    root.context.startActivity(intent)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row_result_job, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount()= listData.size
}