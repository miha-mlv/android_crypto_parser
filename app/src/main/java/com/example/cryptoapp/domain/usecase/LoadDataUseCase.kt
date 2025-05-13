package com.example.cryptoapp.domain.usecase

import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.domain.CoinRepository

class LoadDataUseCase(private val repository: CoinRepository) {

    operator fun invoke() = repository.loadData()

}