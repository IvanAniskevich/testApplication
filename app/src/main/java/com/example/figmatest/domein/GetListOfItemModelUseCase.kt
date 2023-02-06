package com.example.figmatest.domein

import com.example.figmatest.data.ItemModel

class GetListOfItemModelUseCase(private val repository: Repository) {
    suspend operator fun invoke(): List<ItemModel> {
        val r = repository.getRaitings()
        return r
    }
}