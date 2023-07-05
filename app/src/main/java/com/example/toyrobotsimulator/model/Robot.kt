package com.example.toyrobotsimulator.model

data class Robot(var x: Int = 0, var y: Int = 0, var direction: Direction = Direction.NORTH) {
    fun move() {
        when (direction) {
            is Direction.NORTH -> y++
            is Direction.SOUTH -> y--
            is Direction.EAST -> x++
            is Direction.WEST -> x--
        }
    }

    fun turnLeft() {
        direction = when (direction) {
            is Direction.NORTH -> Direction.WEST
            is Direction.SOUTH -> Direction.EAST
            is Direction.EAST -> Direction.NORTH
            is Direction.WEST -> Direction.SOUTH
        }
    }

    fun turnRight() {
        direction = when (direction) {
            is Direction.NORTH -> Direction.EAST
            is Direction.SOUTH -> Direction.WEST
            is Direction.EAST -> Direction.SOUTH
            is Direction.WEST -> Direction.NORTH
        }
    }
}
