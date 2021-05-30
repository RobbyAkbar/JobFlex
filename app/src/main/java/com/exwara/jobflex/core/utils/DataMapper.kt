package com.exwara.jobflex.core.utils

import com.exwara.jobflex.core.data.source.remote.response.PdfResponse
import com.exwara.jobflex.core.domain.model.Pdf

object DataMapper {
    fun mapPdfResponsesToPdfDomain(input: PdfResponse): Pdf {
        return Pdf(
            input.generation,
            input.metageneration,
            input.kind,
            input.selfLink,
            input.mediaLink,
            input.bucket,
            input.storageClass,
            input.size,
            input.md5Hash,
            input.crc32c,
            input.timeStorageClassUpdated,
            input.name,
            input.etag,
            input.timeCreated,
            input.id,
            input.contentType,
            input.updated,
        )
    }
}