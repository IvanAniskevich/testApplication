package com.example.figmatest.domein

import android.util.Log
import com.example.figmatest.data.Raitings
import com.example.figmatest.data.RetrofitInstance
import com.example.figmatest.data.ItemModel
import kotlin.reflect.full.memberProperties

class Repository {
    suspend fun getRaitings(): List<ItemModel> {
        val list = ArrayList<ItemModel>()
        val response = RetrofitInstance.API_SERVICES.getItems()
//        Log.w("wtf","response = ${response}")
        val prop = response::class.memberProperties.forEach {
            val raitings = it.getter.call(response) as Raitings
//            Log.w("wtf","raitings = ${raitings}")
            raitings::class.memberProperties.forEach {
                val value = it.getter.call(raitings) as ItemModel
                list.add(value)
//                Log.w("wtf","list size = ${list.size}")
            }
        }
        return list
    }


}