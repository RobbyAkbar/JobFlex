package com.exwara.jobflex.core.data.source.local.entity

/**
 * Created by robby on 27/05/21.
 */
data class UserEntity(
    var userID: String = "",
    var fullName: String = "",
    var email: String = "",
    var profile_pic: String = "",
    var city: String = "",
    var degree_type: String = "",
    var major: String = "",
    var graduation_date: String = "",
    var total_year_experience: String = "",
    var cv_link: String = "",
    var active: Boolean = false
)
