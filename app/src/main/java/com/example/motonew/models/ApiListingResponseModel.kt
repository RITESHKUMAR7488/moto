package com.example.motonew.models

import java.io.Serializable

data class ApiListingResponseModel(
    val hitsCount: Int,
    val promotedAggregations: List<Any>,
    val records: List<Record>,
    val totalCount: Int,
    val totalCountFormatted: String
):Serializable