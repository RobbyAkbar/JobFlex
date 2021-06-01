package com.exwara.jobflex.core.utils

import com.exwara.jobflex.core.data.source.remote.response.PdfResponse
import com.exwara.jobflex.core.data.source.remote.response.SearchResponseItem
import com.exwara.jobflex.core.domain.model.Pdf
import com.exwara.jobflex.core.domain.model.SearchItem

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

    fun mapSearchResponsesToSearchDomain(input: List<SearchResponseItem> ): List<SearchItem> {
        val dataList = ArrayList<SearchItem>()
        input.map {
            val list = SearchItem(
                startDate = it.startDate,
                description= it.description,
                windowID= it.windowID,
                state= it.state,
                country= it.country,
                title= it.title,
                city= it.city,
                endDate= it.endDate,
                jobID= it.jobID,
                requirements= it.requirements,
                zip5= it.zip5,
            )
            dataList.add(list)
        }
        return dataList
    }
}