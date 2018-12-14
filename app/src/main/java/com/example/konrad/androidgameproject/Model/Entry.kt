package com.example.konrad.androidgameproject.Model

import java.io.Serializable

open class Entry(var id: Int = 1, var type: String) : Serializable {

    open fun generateId() {
    }
}