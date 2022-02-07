package net.roz.core.di

import kotlin.reflect.KClass


@Suppress("UNCHECKED_CAST")
class Container {
    companion object {
        private val bindDict = mutableMapOf<String, BindSetting>()
        private val singletonCache = mutableMapOf<String, Any>()
    }

    init {
        singletonCache[this::class.java.name] = this
    }

    fun <T: Any> bind(clazz: KClass<T>, to: BindSetting): Container {
        clazz.apply {

        }

        bindDict[clazz.qualifiedName!!] = to
        return this
    }

    fun <T: Any> bind(clazz: KClass<T>, to: Any, singleton: Boolean): Container {
        bindDict[clazz.qualifiedName!!] = BindSetting(to, singleton)
        return this
    }

    fun <T: Any> get(clazz: KClass<T>): T {
        return get(clazz.java)
    }

    fun <T: Any> get(clazz: Class<T>): T {
        if (singletonCache[clazz.name] != null) {
            return singletonCache[clazz.name] as T
        }

        if (bindDict[clazz.name] != null) {
            val setting: BindSetting = bindDict[clazz.name]!!

            val instance = resolveBindTarget(setting.target) as T

            if (setting.singleton) {
                singletonCache[clazz.name] = instance
            }

            return instance
        }

        val constructor = clazz.declaredConstructors[0]

        val p: Array<Any> = constructor.parameters
            .map { it2 -> get(it2.type) }
            .toTypedArray()

        return constructor.newInstance(*p) as T
    }

    private fun resolveBindTarget(to: Any): Any {
        return when (to) {
            is KClass<*> -> get(to)
            is Class<*> -> get(to)
            else -> throw Exception("Invalid binding, ${to::class.java.name}")
        }
    }
}