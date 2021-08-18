package com.marcusrunge.stjohannisuelzen.webcontroller.implementation

import com.marcusrunge.stjohannisuelzen.webcontroller.bases.WebControllerBase
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.Control
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.OnWebGoBackSubscriber
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.Sources
import java.lang.ref.WeakReference

internal class ControlImpl(webControllerBase: WebControllerBase) : Control {

    internal companion object {
        var control: Control? = null
        fun create(webControllerBase: WebControllerBase): Control = when {
            control != null -> control!!
            else -> {
                control = ControlImpl(webControllerBase)
                control!!
            }
        }
    }

    val onWebGoBackSubscribers: MutableList<WeakReference<OnWebGoBackSubscriber>> =
        mutableListOf()

    override var canGoBack: Boolean = false

    override fun goBack() {
        if(canGoBack) {
            for (weakRef in onWebGoBackSubscribers) {
                try {
                    weakRef.get()?.onWebGoBack()
                } catch (e: Exception) {
                }
            }
        }
    }

    override fun addOnWebGoBackSubscriber(onWebGoBackSubscriber: OnWebGoBackSubscriber) {
        onWebGoBackSubscribers.add(WeakReference(onWebGoBackSubscriber))
    }

    override fun removeOnWebGoBackSubscriber(onWebGoBackSubscriber: OnWebGoBackSubscriber) {
        val iterator: MutableIterator<WeakReference<OnWebGoBackSubscriber>> =
            onWebGoBackSubscribers.iterator()
        while (iterator.hasNext()) {
            val weakRef: WeakReference<OnWebGoBackSubscriber> = iterator.next()
            if (weakRef.get() === onWebGoBackSubscriber) {
                iterator.remove()
            }
        }
    }
}