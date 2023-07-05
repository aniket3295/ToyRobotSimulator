package com.example.toyrobotsimulator.model

sealed class Direction {
    object NORTH : Direction()
    object SOUTH : Direction()
    object EAST : Direction()
    object WEST : Direction()
}