package com.bcit.lab5jerryxing

class ExcitedPlay : Playable {
    override fun play() {
        println("Your pet is running around excitedly!")
    }
}

class LazyPlay : Playable {
    override fun play() {
        println("Your pet doesn't want to play, they rather lay in the sun.")
    }
}
