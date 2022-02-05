package com.example.currencyz.data.remote.network_module

import com.example.currencyz.data.remote.dto.ApiData

interface NetworkModuleResponses {

    suspend fun getAllData(): ApiData

}