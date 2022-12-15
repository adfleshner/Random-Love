package com.flesh.dataaboutapp.dataaboutapp

open class DataAboutAppRepository {

    var title = ""

    private val internalList = mutableListOf<String>()
    val list get() = internalList.toList()

    protected fun addDataToList(data:String){
        internalList.add(data)
    }

    protected fun removeDataFromList(data:String){
        internalList.remove(data)
    }

    protected fun removeDataFromListAt(index:Int){
        internalList.removeAt(index)
    }

    protected fun clearList(){
        internalList.clear()
    }

}