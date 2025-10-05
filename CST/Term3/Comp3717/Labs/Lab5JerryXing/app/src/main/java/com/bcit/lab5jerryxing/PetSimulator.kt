package com.bcit.lab5jerryxing

class PetSimulator {
    private var invalidChoiceCount: Int by InvalidChoiceCounterDelegate()

    fun start(){
        while(true){
            println()
            println("Choose pet:")
            println("1. Dog\n2. Cat\n3. Exit")
            when (readln().toIntOrNull()) {
                1 -> {
                    val pet = Dog()
                    adopt(pet)
                }
                2 -> {
                    val behaviour = chooseBehaviour() ?: continue
                    val pet = Cat(behaviour)
                    adopt(pet)
                }
                3 -> return
                else -> {
                    invalidChoiceCount++
                }
            }
        }
    }

    private fun chooseBehaviour() : Playable? {
        while (true) {
            println()
            println("Choose a play behaviour for your pet:")
            println("1. Excited\n2. Lazy\n3. Back")
            when (readln().toIntOrNull()) {
                1 -> return ExcitedPlay()
                2 -> return LazyPlay()
                3 -> return null
                else -> invalidChoiceCount++
            }
        }
    }

    private fun adopt(pet: Pet){
        while (true) {
            println()
            println("What would you like to do with ${pet.name}?")
            println("1. Play\n2. Feed\n3. Interact\n4. Whisper\n5. Back ")
            when (readln().toIntOrNull()) {
                1 -> pet.play()
                2 -> pet.feed()
                3 -> pet.interact()
                4 -> pet.whisper {
                    println("You whisper to $name and it responds: $sound")
                }
                5 -> return
                else -> invalidChoiceCount++
            }
        }
    }
}
