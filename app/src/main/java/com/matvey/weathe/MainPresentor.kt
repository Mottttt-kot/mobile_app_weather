package com.matvey.weathe

class MainPresentor : BasePresentor<MainView>() {
    override fun enable() {

    }
    fun refresh(lat:String,lon:String)
    {

        viewState.setLoading(true)
    }
}