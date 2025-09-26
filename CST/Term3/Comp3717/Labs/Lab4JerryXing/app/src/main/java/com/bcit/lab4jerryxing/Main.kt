package com.bcit.lab4jerryxing;

/*
   Jerry Xing
   A01354731
*/

fun main() {
    val manager = MissionManager()

    // Dwarf
    val dwarf = Dwarf()
    val gather = Gather(dwarf)

    manager.select(gather, object : MissionListener {
        override fun missionStart(minion: Minion) {
            val article = if ("AEIOUaeiou".contains(minion.race.first())) "An" else "A"
            println("$article ${minion.race} was sent off to gather some resources")
        }

        override fun missionProgress() {
            println("...")
        }

        override fun missionComplete(minion: Minion, reward: String) {
            val article = if ("AEIOUaeiou".contains(minion.race.first())) "An" else "A"
            if (reward == "nothing") {
                println("$article ${minion.race} has returned from a gather but found nothing!")
            } else {
                println("$article ${minion.race} has returned from a gather and found $reward!")
            }
        }
    })

    println()

    // Elf
    val elf = Elf()
    val hunt = Hunt(elf)

    manager.select(hunt, object : MissionListener {
        override fun missionStart(minion: Minion) {
            val article = if ("AEIOUaeiou".contains(minion.race.first())) "An" else "A"
            println("$article ${minion.race} started a hunt!")
        }

        override fun missionProgress() {
            println("...")
        }

        override fun missionComplete(minion: Minion, reward: String) {
            val article = if ("AEIOUaeiou".contains(minion.race.first())) "An" else "A"
            if (reward == "nothing") {
                println("$article ${minion.race} has returned from a hunt, but found nothing!")
            } else {
                println("$article ${minion.race} has returned from a hunt, and found $reward!")
            }
        }
    })
}
