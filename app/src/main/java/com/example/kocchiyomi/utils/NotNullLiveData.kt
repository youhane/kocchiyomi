package com.example.kocchiyomi.utils

import androidx.lifecycle.LiveData

open class NotNullLiveData<T : Any>(value: T) : LiveData<T>(value) {
    override fun getValue(): T = super.getValue()!!
    override fun setValue(value: T) = super.setValue(value)
    override fun postValue(value: T) = super.postValue(value)
}

class NotNullMutableLiveData<T : Any>(value: T) : NotNullLiveData<T>(value) {
    public override fun setValue(value: T) = super.setValue(value)
    public override fun postValue(value: T) = super.postValue(value)
}