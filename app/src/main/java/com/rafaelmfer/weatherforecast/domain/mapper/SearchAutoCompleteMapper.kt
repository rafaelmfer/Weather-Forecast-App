package com.rafaelmfer.weatherforecast.domain.mapper

import com.rafaelmfer.weatherforecast.data.remote.response.SearchAutoCompleteResponseItem
import com.rafaelmfer.weatherforecast.domain.model.SearchAutoCompleteModelItem

fun SearchAutoCompleteResponseItem.asDomainModel(): SearchAutoCompleteModelItem {
    return SearchAutoCompleteModelItem(
        id = id,
        name = name,
        region = region
    )
}