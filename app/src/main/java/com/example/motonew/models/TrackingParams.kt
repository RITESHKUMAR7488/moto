package com.example.motonew.models

data class TrackingParams(
    val dealerGroupUniqueName: String,
    val dealerGroupUuid: String,
    val dealerName: String,
    val dealerUniqueName: String,
    val dealerUuid: String,
    val experience: String,
    val idFromProvider: String,
    val remoteDealerId: String,
    val remoteSku: String,
    val rooftopUniqueName: Any,
    val rooftopUuid: Any
)