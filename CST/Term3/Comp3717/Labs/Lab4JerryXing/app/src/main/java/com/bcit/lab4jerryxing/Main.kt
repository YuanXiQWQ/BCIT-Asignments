package com.bcit.lab4jerryxing

/*
   Jerry Xing
   A01354731
*/

/**
 * Returns "An" if the race starts with a vowel, otherwise returns "A"
 */
private fun articleFor(race: String): String =
    if ("AEIOUaeiou".contains(race.first())) "An" else "A"

/**
 * Reads a choice from the user
 */
private fun readChoice(prompt: String, options: Map<String, String>): String {
    while (true) {
        println(prompt)
        options.forEach { (k, v) -> println("[$k] $v") }
        print("> ")
        when (val s = readLine()?.trim()) {
            in options.keys -> return options[s]!!
            else -> println("Invalid choice, try again.\n")
        }
    }
}

/**
 * Reads an integer from the user
 */
private fun readInt(prompt: String, min: Int = 1, max: Int = 10): Int {
    while (true) {
        print("$prompt ($min..$max)\n> ")
        val n = readLine()?.trim()?.toIntOrNull()
        if (n != null && n in min..max) return n
        println("Invalid number, try again.\n")
    }
}

/**
 * Main
 */
fun main() {
    val manager = MissionManager()

    // Choose minion
    val race = readChoice(
        prompt = "Choose a minion race:",
        options = mapOf("1" to "Dwarf", "2" to "Elf")
    )
    val minion: Minion = if (race == "Dwarf") Dwarf() else Elf()

    // Choose mission
    val missionType = readChoice(
        prompt = "Choose a mission:",
        options = mapOf("1" to "Gather", "2" to "Hunt")
    )

    // Common anonymous MissionListener
    val listener = object : MissionListener {
        override fun missionStart(minion: Minion) {
            val a = articleFor(minion.race)
            if (missionType == "Gather") {
                println("$a ${minion.race} was sent off to gather some resources")
            } else {
                println("$a ${minion.race} started a hunt!")
            }
        }

        override fun missionProgress() {
            println("...")
        }

        override fun missionComplete(minion: Minion, reward: String) {
            val a = articleFor(minion.race)
            if (missionType == "Gather") {
                if (reward == "nothing") {
                    println("$a ${minion.race} has returned from a gather but found nothing!")
                } else {
                    println("$a ${minion.race} has returned from a gather and found $reward!")
                }
            } else {
                if (reward == "nothing") {
                    println("$a ${minion.race} has returned from a hunt, but found nothing!")
                } else {
                    println("$a ${minion.race} has returned from a hunt, and found $reward!")
                }
            }
        }
    }

    // Create and run the mission
    if (missionType == "Gather") {
        val times = readInt("How many times to repeat gather?", min = 1)
        val gather = Gather(minion)
        manager.selectRepeatable(gather, times, listener)
    } else {
        val hunt = Hunt(minion)
        manager.select(hunt, listener)
    }
}
