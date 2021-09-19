package com.marcusrunge.stjohannisuelzen.bases

import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel

abstract class ViewModelBase : ViewModel(), Observable {
    companion object {
        const val UPDATE_VIEW: Int = 1
    }

    private val propertyChangeRegistry = PropertyChangeRegistry()
    protected val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(inputMessage: Message) {
            when (inputMessage.what) {
                UPDATE_VIEW -> updateView(inputMessage)
            }
        }
    }

    abstract fun updateView(inputMessage: Message)

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        propertyChangeRegistry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        propertyChangeRegistry.remove(callback)
    }

    protected fun notifyPropertiesChanged() {
        synchronized(this) {
            propertyChangeRegistry.notifyCallbacks(this, 0, null)
        }
    }

    protected fun notifyPropertyChanged(propertyId: Int) {
        synchronized(this) {
            propertyChangeRegistry.notifyCallbacks(this, propertyId, null)
        }
    }
}