package com.bcit.lab4jerryxing

/**
 * Gather mission for a minion
 */
class Gather(minion: Minion) : Mission(minion), Repeatable {

    /**
     * Determine the mission time based on the minion's backpack size and base speed
     */
    override fun determineMissionTime(): Int {
        val factor = (0..4).random()
        return (minion.backpackSize + minion.baseSpeed) * factor
    }

    /**
     * Determine the reward based on the time taken
     */
    override fun reward(time: Int): String = when (time) {
        in 10..21 -> "bronze"
        in 22..33 -> "silver"
        in 34..50 -> "gold"
        else -> "nothing"
    }

    /**
     * Repeat the mission a specified number of times
     */
    override fun repeat(times: Int, listener: MissionListener) {
        repeat(times) { start(listener) }
    }
}
