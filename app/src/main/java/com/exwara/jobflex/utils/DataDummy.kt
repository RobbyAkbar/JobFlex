package com.exwara.jobflex.utils

import com.exwara.jobflex.data.source.local.entity.JobEntity
import com.exwara.jobflex.ui.home.SliderItem

/**
 * Created by robby on 27/05/21.
 */
object DataDummy {

    fun generateDummyJobs(): ArrayList<JobEntity> {
        val jobs = ArrayList<JobEntity>()
        jobs.add(
            JobEntity(
                1,
                "Security Engineer/Technical Lead",
                "Conveniently evisculate ubiquitous growth strategies after multifunctional total linkage. Uniquely deploy progressive.",
                "skill",
                listOf("Full Time", "Min. 1 Year", "Mid Level"),
                "washington",
                "2020",
                "2021"
            )
        )
        jobs.add(
            JobEntity(
                1,
                "Security Engineer/Technical Lead",
                "Conveniently evisculate ubiquitous growth strategies after multifunctional total linkage. Uniquely deploy progressive.",
                "skill",
                listOf("Full Time", "Min. 1 Year", "Mid Level"),
                "washington",
                "2020",
                "2021"
            )
        )
        jobs.add(
            JobEntity(
                1,
                "Security Engineer/Technical Lead",
                "Conveniently evisculate ubiquitous growth strategies after multifunctional total linkage. Uniquely deploy progressive.",
                "skill",
                listOf("Full Time", "Min. 1 Year", "Mid Level"),
                "washington",
                "2020",
                "2021"
            )
        )
        jobs.add(
            JobEntity(
                1,
                "Security Engineer/Technical Lead",
                "Conveniently evisculate ubiquitous growth strategies after multifunctional total linkage. Uniquely deploy progressive.",
                "skill",
                listOf("Full Time", "Min. 1 Year", "Mid Level"),
                "washington",
                "2020",
                "2021"
            )
        )
        jobs.add(
            JobEntity(
                1,
                "Security Engineer/Technical Lead",
                "Conveniently evisculate ubiquitous growth strategies after multifunctional total linkage. Uniquely deploy progressive.",
                "skill",
                listOf("Full Time", "Min. 1 Year", "Mid Level"),
                "washington",
                "2020",
                "2021"
            )
        )
        jobs.add(
            JobEntity(
                1,
                "Security Engineer/Technical Lead",
                "Conveniently evisculate ubiquitous growth strategies after multifunctional total linkage. Uniquely deploy progressive.",
                "skill",
                listOf("Full Time", "Min. 1 Year", "Mid Level"),
                "washington",
                "2020",
                "2021"
            )
        )
        jobs.add(
            JobEntity(
                1,
                "Security Engineer/Technical Lead",
                "Conveniently evisculate ubiquitous growth strategies after multifunctional total linkage. Uniquely deploy progressive.",
                "skill",
                listOf("Full Time", "Min. 1 Year", "Mid Level"),
                "washington",
                "2020",
                "2021"
            )
        )
        return jobs
    }

    fun generateDummySlider(): ArrayList<SliderItem> {
        val items = ArrayList<SliderItem>()
        items.add(
            SliderItem(
                "https://www.w3schools.com/css/img_5terre.jpg",
                "Add a description of the image 1 here"
            )
        )
        items.add(
            SliderItem(
                "https://www.w3schools.com/css/img_forest.jpg",
                "Add a description of the image 2 here"
            )
        )
        items.add(
            SliderItem(
                "https://www.w3schools.com/css/img_lights.jpg",
                "Add a description of the image 3 here"
            )
        )
        items.add(
            SliderItem(
                "https://www.w3schools.com/css/img_mountains.jpg",
                "Add a description of the image 4 here"
            )
        )
        items.add(
            SliderItem(
                "https://www.w3schools.com/css/paris.jpg",
                "Add a description of the image 5 here"
            )
        )
        return items
    }

}