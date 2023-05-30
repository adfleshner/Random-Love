package com.flesh.dataaboutapp.dataaboutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flesh.dataaboutapp.dataaboutapp.models.BaseDataAboutAppObject
import com.flesh.dataaboutapp.databinding.ListItemDataAboutAppSimpleBinding

private object BaseDataAboutAppDiffUtil : DiffUtil.ItemCallback<BaseDataAboutAppObject>() {
    override fun areItemsTheSame(
        oldItem: BaseDataAboutAppObject,
        newItem: BaseDataAboutAppObject
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: BaseDataAboutAppObject,
        newItem: BaseDataAboutAppObject
    ): Boolean {
        if(oldItem is BaseDataAboutAppObject.SimpleDataAboutAppObject && newItem is BaseDataAboutAppObject.SimpleDataAboutAppObject){
            return oldItem.simpleData == newItem.simpleData
        }
        return false
    }

}


class DataAboutAppAdapter :ListAdapter<BaseDataAboutAppObject, SimpleDataAboutAppViewHolder>(
    BaseDataAboutAppDiffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleDataAboutAppViewHolder {
        return SimpleDataAboutAppViewHolder(ListItemDataAboutAppSimpleBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SimpleDataAboutAppViewHolder, position: Int) {
        holder.bind(getItem(position) as BaseDataAboutAppObject.SimpleDataAboutAppObject)
    }
}



class SimpleDataAboutAppViewHolder(private val binding: ListItemDataAboutAppSimpleBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data : BaseDataAboutAppObject.SimpleDataAboutAppObject){
        binding.tvSimpleData.text = data.simpleData
    }
}