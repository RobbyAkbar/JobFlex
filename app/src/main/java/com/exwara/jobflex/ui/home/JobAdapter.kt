package com.exwara.jobflex.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exwara.jobflex.core.domain.model.SearchItem
import com.exwara.jobflex.core.utils.AppUtils
import com.exwara.jobflex.databinding.ItemRowJobBinding
import com.exwara.jobflex.ui.detail.DetailActivity
import java.util.*
import kotlin.math.min

/**
 * Created by robby on 27/05/21.
 */
class JobAdapter : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    private var listItems: List<SearchItem> = Collections.emptyList()
    var itemSize: Int = 0

    fun updateWith(dataItems: List<SearchItem>, itemSize: Int) {
        this.listItems = dataItems
        this.itemSize = itemSize
        notifyDataSetChanged()
    }

    inner class JobViewHolder(private val binding: ItemRowJobBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(job: SearchItem) {
            val qualificationAdapter = QualificationAdapter(listOf("Full Time", "Min. 1 Year", "Mid Level"))
            with(binding) {
                letterTitle.text = job.title[0].toString()
                tvItemName.text = job.title
                tvItemName.isSelected = true
                tvItemDetail.text = HtmlCompat.fromHtml(job.description,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )

                with(rvQualification) {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(true)
                    adapter = qualificationAdapter
                }

                itemView.setOnClickListener{
                    val intent = Intent(root.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DATA, job)
                    root.context.startActivity(intent)
                }

                btnApply.setOnClickListener {
                    AppUtils.sendSampleDialog(root.context, "Your application has been submitted!")
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