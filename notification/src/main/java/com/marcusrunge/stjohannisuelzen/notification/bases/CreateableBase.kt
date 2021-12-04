package com.marcusrunge.stjohannisuelzen.notification.bases

internal abstract class CreateableBase {
    companion object {
        private var instance: Any? = null
        inline fun <TInterface, reified TClass> create(notificationBase: NotificationBase): Any where TClass : TInterface {
            if (instance == null) {
                instance =
                    Class::class.constructors.find { it.parameters.isEmpty() }?.call() as TInterface
                (instance as CreateableBase).OnCreate(notificationBase)
            }
            return instance!!
        }
    }

    protected abstract fun OnCreate(notificationBase: NotificationBase)
}