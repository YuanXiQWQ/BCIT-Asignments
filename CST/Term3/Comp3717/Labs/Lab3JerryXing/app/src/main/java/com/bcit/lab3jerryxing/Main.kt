package com.bcit.lab3jerryxing

/*
    Jerry Xing
    A01354731
*/

fun main() {
    val facts: Map<Int, String> = linkedMapOf(
        1492 to "Christopher Columbus discovers America",
        1601 to "William Shakespeare writes Hamlet",
        1632 to "Galileo discovered the acceleration of gravity on Earth to be 9.8m/s",
        1838 to "Roughly 9.46 trillion km, the light-year is first used as a measurement in astronomy",
        2020 to "Covid 19 Pandemic"
    )

    printMap(facts)
    println()

    /**
     * Anonymous function
     */
    val anonymousPrinter = fun(value: String?) {
        println(value ?: "(no fact)")
    }
    anonymousPrinter(facts[1492])

    /**
     * Lambda function
     */
    val lambdaPrinter: (Int, String) -> Unit = { year, value ->
        if (year > 0) println(value)
    }
    facts[1601]?.let { lambdaPrinter(1601, it) }

    /**
     * Function as an argument
     */
    printWithTransformer(facts.getValue(1632)) { it }

    /**
     * Function reference
     */
    facts[1838].let(::println)

    /**
     * "Somehow" function :>
     */
    val lastLine = valueFromPair(2020 to facts.getValue(2020))
    println(lastLine)
}

/**
 * Function to print map
 */
fun printMap(history: Map<Int, String>) {
    history.toSortedMap().forEach { (year, fact) ->
        println("$year=$fact")
    }
}

/**
 * Function to print with transformer
 */
fun printWithTransformer(value: String, transformer: (String) -> String) {
    println(transformer(value))
}

/**
 * Function to get value from pair
 */
fun valueFromPair(entry: Pair<Int, String>): String {
    require(entry.first > 0) { "Year must be positive" }
    return entry.second
}
