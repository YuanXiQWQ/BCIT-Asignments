package com.bcit.lab2jerryxing

/*
    Jerry Xing
    A01354731
*/

/**
 * Main function
 *
 * @return void
 */
fun main() {
    val wordArray = arrayOf("Day", "before", "yesterday", "I", "saw", "a", "rabbit", "and", "yesterday", "a", "deer", "and", "today", "you")
    val lengthList = mutableListOf<Int>()

    // Calculate the length of each word
    var index = 0
    while (index < wordArray.size) {
        val word = wordArray[index]
        if (word.isBlank()) {
            index++
            continue
        }
        lengthList.add(word.length)
        index++
    }

    // Collect the longest words
    val maxLen = lengthList.maxOrNull() ?: 0
    val longestWords = mutableListOf<String>()
    for (w in wordArray) {
        if (w.length == maxLen) longestWords.add(w)
    }

    // Collect the shortest words
    val minLen = lengthList.minOrNull() ?: 0
    val shortestWords = mutableListOf<String>()
    wordArray.forEach { w ->
        if (w.length == minLen) shortestWords.add(w)
    }

    println("Words: ${wordArray.contentToString()}")
    println("Word lengths: $lengthList")
    println("Longest word(s): $longestWords")
    println("Shortest word(s): $shortestWords")
}