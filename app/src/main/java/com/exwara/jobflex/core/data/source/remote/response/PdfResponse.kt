package com.exwara.jobflex.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PdfResponse(
	@field:SerializedName("generation")
	val generation: String,

	@field:SerializedName("metageneration")
	val metageneration: String,

	@field:SerializedName("kind")
	val kind: String,

	@field:SerializedName("selfLink")
	val selfLink: String,

	@field:SerializedName("mediaLink")
	val mediaLink: String,

	@field:SerializedName("bucket")
	val bucket: String,

	@field:SerializedName("storageClass")
	val storageClass: String,

	@field:SerializedName("size")
	val size: String,

	@field:SerializedName("md5Hash")
	val md5Hash: String,

	@field:SerializedName("crc32c")
	val crc32c: String,

	@field:SerializedName("timeStorageClassUpdated")
	val timeStorageClassUpdated: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("etag")
	val etag: String,

	@field:SerializedName("timeCreated")
	val timeCreated: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("contentType")
	val contentType: String,

	@field:SerializedName("updated")
	val updated: String
)
