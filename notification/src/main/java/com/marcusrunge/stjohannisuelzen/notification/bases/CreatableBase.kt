package com.marcusrunge.stjohannisuelzen.notification.bases

internal abstract class CreatableBase<TInterface, TClass> where TClass : TInterface{
    companion object {
        private var instance: TInterface? = null
        fun create(notificationBase: NotificationBase): TInterface = when {
            instance != null -> instance!!
            else -> {
                instance = TClass()
                instance!!
            }
            (instance as CreatableBase).OnCreate(notificationBase)
        }
    }

    protected abstract fun OnCreate(notificationBase: NotificationBase)
}