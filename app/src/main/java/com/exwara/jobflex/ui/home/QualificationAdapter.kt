package com.exwara.jobflex.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exwara.jobflex.databinding.ItemRowQualificationBinding

/**
 * Created by robby on 27/05/21.
 */
class QualificationAdapter(private val listItems: List<String>): RecyclerView.Adapter<QualificationAdapter.QualificationViewHolder>() {

    inner class QualificationViewHolder(private val binding: ItemRowQualificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String){
            with(binding) {
                tvQualification.text = item
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QualificationViewHolder {
        val itemsQualificationBinding = ItemRowQualificationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return QualificationViewHolder(itemsQualificationBinding)
    }

    override fun onBindViewHolder(holder: QualificationViewHolder, position: Int) {
        holder.bind(listItems[position])
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

}