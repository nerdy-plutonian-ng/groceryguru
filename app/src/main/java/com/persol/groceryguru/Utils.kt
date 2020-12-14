package com.persol.groceryguru

class Utils {

    fun generateID() : Int {
        return (Math.random() * 9000000).toInt() + 1000000
    }
}