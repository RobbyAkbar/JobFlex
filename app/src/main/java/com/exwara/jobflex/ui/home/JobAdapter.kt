package com.exwara.jobflex.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exwara.jobflex.data.source.local.entity.JobEntity
import com.exwara.jobflex.databinding.ItemRowJobBinding
import java.util.*
import kotlin.math.min

/**
 * Created by robby on 27/05/21.
 */
class JobAdapter : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    private var listItems: List<JobEntity> = Collections.emptyList()
    private var itemSize: Int = 0

    fun updateWith(dataItems: List<JobEntity>, itemSize: Int) {
        this.listItems = dataItems
        this.itemSize = itemSize
        notifyDataSetChanged()
    }

    inner class JobViewHolder(private val binding: ItemRowJobBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(job: JobEntity) {
            val qualificationAdapter = QualificationAdapter(job.qualification)
            with(binding) {
                letterTitle.text = job.title[0].toString()
                tvItemName.text = job.title
                tvItemName.isSelected = true
                tvItemDetail.text = job.description

                with(rvQualification) {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(true)
                    adapter = qualificationAdapter
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val itemsJobBinding = ItemRowJobBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return JobViewHolder(itemsJobBinding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(listItems[position])
    }

    override fun getItemCount(): Int {
        return min(listItems.size, itemSize)
    }

}