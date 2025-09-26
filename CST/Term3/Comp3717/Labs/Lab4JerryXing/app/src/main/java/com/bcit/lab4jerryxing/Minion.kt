package com.bcit.lab4jerryxing

/**
 * Base class for all minions
 */
abstract class Minion {
    abstract val race: String
    abstract val baseHealth: Int
    abstract val baseSpeed: Int
    abstract val backpackSize: Int
    abstract val catchphrase: String
}
