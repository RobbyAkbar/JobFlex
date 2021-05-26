package com.exwara.jobflex.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.exwara.jobflex.R
import com.exwara.jobflex.databinding.CarouselItemContainerBinding

/**
 * Created by robby on 27/05/21.
 */
class SliderAdapter : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    var listItems = ArrayList<SliderItem>()
        set(listItems) {
            this.listItems.clear()
            if (listItems.size > 0) {
                this.listItems.addAll(listItems)
            }
            notifyDataSetChanged()
        }

    inner class SliderViewHolder(private val binding: CarouselItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SliderItem) {
            with(binding) {
                tvDescription.text = item.description
                Glide.with(itemView.context)
                    .load(item.image_url)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_image_search)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(imgCarousel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val itemsCarouselBinding = CarouselItemContainerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SliderViewHolder(itemsCarouselBinding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(listItems[position])
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

}