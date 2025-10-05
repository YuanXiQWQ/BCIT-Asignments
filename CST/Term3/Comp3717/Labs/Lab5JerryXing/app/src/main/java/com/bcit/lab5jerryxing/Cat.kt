package com.bcit.lab5jerryxing

class Cat(private val playBehaviour: Playable) : Pet, Playable by playBehaviour {

    override val name: String
        get() = "Whiskers"

    override val sound: String
        get() = "Meow"

    override fun interact() {
        println("$name is purring softly, $sound")
    }

    override fun feed() {
        println("$name enjoys some tuna")
    }
}
