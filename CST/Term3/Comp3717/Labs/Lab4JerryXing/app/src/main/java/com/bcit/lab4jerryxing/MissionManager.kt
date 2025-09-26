package com.bcit.lab4jerryxing

/**
 * Mission manager for managing missions
 */
class MissionManager {
    fun select(mission: Mission, listener: MissionListener) {
        mission.start(listener)
    }

    fun selectRepeatable(repeatable: Repeatable, times: Int, listener: MissionListener) {
        repeatable.repeat(times, listener)
    }
}
