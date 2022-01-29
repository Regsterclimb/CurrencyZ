package com.example.currencyz.data.remote.network_module

import com.example.currencyz.data.remote.dto.apiData

interface NetworkModuleResponses {

    suspend fun getAllData(): apiData

}