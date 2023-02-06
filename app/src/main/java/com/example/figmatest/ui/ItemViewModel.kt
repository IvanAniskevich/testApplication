package com.example.figmatest.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.figmatest.data.ItemModel
import com.example.figmatest.domein.GetListOfItemModelUseCase
import kotlinx.coroutines.launch

class ItemViewModel(
    private val getListOfItemModelUseCase: GetListOfItemModelUseCase
) : ViewModel() {


    private var _listX0 = MutableLiveData<List<ItemModel>>()
    val listItemModel: LiveData<List<ItemModel>> get() = _listX0

    var lottiProgress = MutableLiveData<Float>()
    var pbPrgress = MutableLiveData<Int>()

    init {
        getListX0()
    }

    fun getListX0() {
        viewModelScope.launch {
            try {
                val list = getListOfItemModelUseCase()
                _listX0.value = list
                Log.w("wtf", "viewModel get list  = ${list}")
            } catch (e: Exception) {
                _listX0.value = emptyList()
                Log.w("wtf", "viewModel get list exception = $e")
            }
        }
    }

    fun getLottiProgress(): Float? = lottiProgress.value
    fun setLottiProgress(progress: Float) {
        lottiProgress.value = progress
    }

    fun getPbProgress(): Int? = pbPrgress.value
    fun setPbProgress(progress: Int) {
        pbPrgress.value = progress
    }
}