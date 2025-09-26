package com.bcit.lab4jerryxing

/**
 * Interface for repeatable missions
 */
interface Repeatable {
    fun repeat(times: Int, listener: MissionListener)
}
