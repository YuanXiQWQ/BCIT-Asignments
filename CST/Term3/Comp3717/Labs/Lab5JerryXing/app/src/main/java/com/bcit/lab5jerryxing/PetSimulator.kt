package com.bcit.lab5jerryxing

class PetSimulator {

    private var invalidChoiceCount = 0

    fun start(){
        while(true){
            println()
            println("Choose pet:")
            println("1. Dog\n2. Exit")
            when (readln().toIntOrNull()) {
                1 -> {
                  val pet = Dog()
                  adopt(pet)
                }
                2 -> return
                else -> {
                  invalidChoiceCount++
                }
            }
        }
    }

    private fun chooseBehaviour() : Playable? {
        TODO("Not implemented yet")
    }

    private fun adopt(pet: Pet){
        while (true) {
            println()
            println("What would you like to do with ${pet.name}?")
            println("1. Play\n2. Feed\n3. Interact\n4. Back")
            when (readln().toIntOrNull()) {
                1 -> pet.play()
                2 -> pet.feed()
                3 -> pet.interact()
                4 -> return
                else -> {
                  invalidChoiceCount++
                }
            }
        }
    }

}


