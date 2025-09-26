package com.bcit.lab4jerryxing

/**
 * Hunt mission for a minion
 */
class Hunt(minion: Minion) : Mission(minion) {
    /**
     * Determine the mission time based on the minion's base health and base speed
     */
    override fun determineMissionTime(): Int {
        val factor = (0..4).random()
        return (minion.baseHealth + minion.baseSpeed) * factor
    }

    /**
     * Determine the reward based on the time taken
     */
    override fun reward(time: Int): String = when (time) {
        in 11..20 -> "mouse"
        in 21..30 -> "fox"
        in 31..50 -> "buffalo"
        else -> "nothing"
    }
}
