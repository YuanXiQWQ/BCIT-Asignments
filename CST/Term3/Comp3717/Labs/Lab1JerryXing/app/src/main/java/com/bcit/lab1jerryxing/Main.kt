package com.bcit.lab1jerryxing

/*
 * Jerry Xing
 * A01354731
 */

// Const
const val VERSION_NUM: Double = 0.2
const val SLOGAN: String = "deliver with a smile."

/**
 * Main function
 * @return void
 */
fun main() {
    // Var
    val streetNum: Int = 123
    val streetName: String = "loch ness road"

    // Full address, constructed if version is 1.0 or greater
    val fullAddress: String? = if (VERSION_NUM >= 1.0) {
        String.format("%d %s, glasgow, scotland", streetNum, streetName)
    } else {
        // Beta message when less than 1.0
        println(
            """
            Starting beta version...
            ...
            ...
            ...
            """.trimIndent()
        )
        null
    }

    val title: String = String.format("%s v%.1f", "Food Delivery Service", VERSION_NUM)
    val message: String = """
        
                $title
        
        Welcome to Glasgow's finest food delivery service, where we provide
        you with swift instructions on where to deliver your food.
        
                Please deliver the food to:
        
                ${fullAddress?.uppercase() ?: "null"}
        
        Your hard work and commitment to delivering food are
        always appreciated, and never forget..${SLOGAN.uppercase()}
        
        ...Thank you.
    """.trimIndent()

    println(message)
}

