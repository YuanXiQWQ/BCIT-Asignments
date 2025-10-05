package com.bcit.lab5jerryxing

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.system.exitProcess

class InvalidChoiceCounterDelegate : ReadWriteProperty<Any?, Int> {
    private var count: Int = 0

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int = count

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        if (value > count) {
            count = value
            when (count) {
                1 -> println("Oops! That's not a valid option. Please try again.")
                2 -> println("Oops. That's not a valid option. One more incorrect attempt and the program will exit")
                3 -> {
                    println("Oops. That's not a valid option. Too many invalid attempts. Goodbye!")
                    exitProcess(0)
                }
                else -> {}
            }
        } else {
            count = value
        }
    }
}
