package com.exwara.jobflex.core.utils

import com.exwara.jobflex.ui.home.SliderItem

/**
 * Created by robby on 27/05/21.
 */
object DataDummy {

    fun generateDummySlider(): ArrayList<SliderItem> {
        val items = ArrayList<SliderItem>()
        items.add(
            SliderItem(
                "https://thumbs.dreamstime.com/z/now-hiring-vacancy-concept-poster-template-outsource-team-hire-creative-employee-career-promotion-red-loudspeaker-job-137192142.jpg",
                "Now Hiring Vacancy Concept Poster Template."
            )
        )
        items.add(
            SliderItem(
                "https://thumbs.dreamstime.com/z/hiring-vacancy-advertisement-template-trendy-job-banner-poster-flyer-yellow-white-black-colors-minimalistic-187792107.jpg",
                "We are hiring vacancy advertisement template."
            )
        )
        items.add(
            SliderItem(
                "https://thumbs.dreamstime.com/z/now-hiring-job-opportunity-concept-banner-template-vacancy-promotion-advertising-leaflet-career-to-recruiting-team-purple-137192135.jpg",
                "Now Hiring Job Opportunity Concept Banner Template."
            )
        )
        return items
    }

}