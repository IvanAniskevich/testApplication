package com.example.figmatest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.figmatest.R
import com.example.figmatest.data.ItemModel

import com.example.figmatest.databinding.ItemRvBinding


class RVAdapter : RecyclerView.Adapter<RVAdapter.RVViewHolder>() {

    var adapterList: List<ItemModel> = listOf()

    class RVViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemRvBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv, parent, false)
        return RVViewHolder(view)
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        val item = adapterList[position]
        with(holder.binding) {
            image.load(item.image)
            titleTV.text = item.title
        }
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }

    fun setList(list: List<ItemModel>) {
        adapterList = list
        notifyDataSetChanged()
    }


}