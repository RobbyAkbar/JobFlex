package com.exwara.jobflex.core.data.source.local.entity

/**
 * Created by robby on 27/05/21.
 */
data class JobEntity(

    var job_id: Int,
    var title: String,
    var description: String,
    var requirements: String,
    var qualification: List<String>,
    var city: String,
    var start_date: String,
    var end_date: String

)
