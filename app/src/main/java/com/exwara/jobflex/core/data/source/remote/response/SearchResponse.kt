package com.exwara.jobflex.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResponse(
	@field:SerializedName("StartDate")
	val startDate: String,

	@field:SerializedName("Description")
	val description: String,

	@field:SerializedName("WindowID")
	val windowID: Int,

	@field:SerializedName("State")
	val state: String,

	@field:SerializedName("Country")
	val country: String,

	@field:SerializedName("Title")
	val title: String,

	@field:SerializedName("City")
	val city: String,

	@field:SerializedName("EndDate")
	val endDate: String,

	@field:SerializedName("JobID")
	val jobID: Int,

	@field:SerializedName("Requirements")
	val requirements: String,

	@field:SerializedName("Zip5")
	val zip5: Int
) : Parcelable
