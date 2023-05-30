package com.flesh.dataaboutapp.dataaboutapp.models

sealed class BaseDataAboutAppObject {
    class SimpleDataAboutAppObject(val simpleData : String) : BaseDataAboutAppObject()
}