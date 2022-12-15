package io.shits.and.gigs.randomcodinglove.dataaboutapp.models

sealed class BaseDataAboutAppObject {
    class SimpleDataAboutAppObject(val simpleData : String) : BaseDataAboutAppObject()
}