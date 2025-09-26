package com.bcit.lab4jerryxing

/**
 * Interface for mission listeners
 */
interface MissionListener {
    fun missionStart(minion: Minion)
    fun missionProgress()
    fun missionComplete(minion: Minion, reward: String)
}
