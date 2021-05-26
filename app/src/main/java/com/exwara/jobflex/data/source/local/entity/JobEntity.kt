package com.exwara.jobflex.data.source.local.entity

/**
 * Created by robby on 27/05/21.
 */
data class JobEntity(

    var job_id: Int,
    var title: String,
    var description: String,
    var job_icon: String,
    var requirements: String,
    var city: String,
    var start_date: String,
    var end_date: String

)
