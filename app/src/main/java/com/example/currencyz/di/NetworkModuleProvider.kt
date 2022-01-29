package com.example.currencyz.di

import com.example.currencyz.data.remote.network_module.NetworkModule

interface NetworkModuleProvider {

    fun provideNetworkModule() : NetworkModule

}