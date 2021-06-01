package com.exwara.jobflex.core.domain.model

data class PdfItem(
    val generation: String,
    val metageneration: String,
    val kind: String,
    val selfLink: String,
    val mediaLink: String,
    val bucket: String,
    val storageClass: String,
    val size: String,
    val md5Hash: String,
    val crc32c: String,
    val timeStorageClassUpdated: String,
    val name: String,
    val etag: String,
    val timeCreated: String,
    val id: String,
    val contentType: String,
    val updated: String
)