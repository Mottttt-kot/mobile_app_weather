package com.matvey.weathe

import moxy.MvpPresenter
import moxy.MvpView

abstract class BasePresentor<T:MvpView> : MvpPresenter<T>() {
    open fun enable()
    {

    }
}