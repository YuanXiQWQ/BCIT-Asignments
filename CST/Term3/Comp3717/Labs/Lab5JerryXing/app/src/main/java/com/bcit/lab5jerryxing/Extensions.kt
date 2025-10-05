package com.bcit.lab5jerryxing

fun Pet.whisper(block: Pet.() -> Unit) {
    this.block()
}
