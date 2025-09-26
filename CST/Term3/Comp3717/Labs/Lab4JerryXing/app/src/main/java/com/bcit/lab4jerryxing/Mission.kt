package com.bcit.lab4jerryxing

/**
 * Base class for all missions
 */
abstract class Mission(protected val minion: Minion) {

    init {
        println(minion.catchphrase)
    }

    /**
     * Start the mission
     */
    fun start(listener: MissionListener) {
        listener.missionStart(minion)

        repeat(3) {
            listener.missionProgress()
            Thread.sleep(60)
        }

        val time = determineMissionTime()
        val prize = reward(time)
        listener.missionComplete(minion, prize)
    }

    protected abstract fun determineMissionTime(): Int
    protected abstract fun reward(time: Int): String
}
