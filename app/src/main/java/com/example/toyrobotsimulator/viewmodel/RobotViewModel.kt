package com.example.toyrobotsimulator.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toyrobotsimulator.R
import com.example.toyrobotsimulator.adapter.GridAdapter
import com.example.toyrobotsimulator.model.Direction
import com.example.toyrobotsimulator.model.Robot

class RobotViewModel : ViewModel() {

    private val mutableRobotPosition = MutableLiveData<Robot>()
    val robotPosition: LiveData<Robot>
        get() = mutableRobotPosition
    private val mutableIsValidMove = MutableLiveData<Boolean>()
    val isValidMove: LiveData<Boolean>
        get() = mutableIsValidMove
    private var previousDirection: Robot? = null

    lateinit var gridAdapter: GridAdapter

    fun attachGridAdapter(adapter: GridAdapter) {
        gridAdapter = adapter
    }

    fun placeRobot(x: Int, y: Int, direction: String ) {
        val placedDirection: Direction? = when (direction) {
            "NORTH" -> Direction.NORTH
            "EAST" -> Direction.EAST
            "SOUTH" -> Direction.SOUTH
            "WEST" -> Direction.WEST
            else -> null
        }
        val robot = placedDirection?.let { Robot(x, y, it) }
        mutableRobotPosition.value = robot?.copy()
        updateGridCell()
    }

    fun moveRobot() {
        val currentPosition = mutableRobotPosition.value ?: Robot()
        val newPosition = calculateNewPosition(currentPosition)

        if (newPosition != null) {
            mutableRobotPosition.value = newPosition.copy()
            previousDirection = newPosition.copy()
            updateGridCell()
        }else {
            mutableRobotPosition.value = previousDirection?.copy()
            mutableIsValidMove.value = false
        }
    }

    fun turnLeft() {
        val currentPosition = mutableRobotPosition.value ?: Robot()
        previousDirection = currentPosition.copy()
        currentPosition.turnLeft()
        mutableRobotPosition.value = currentPosition.copy()
    }

    fun turnRight() {
        val currentPosition = mutableRobotPosition.value ?: Robot()
        previousDirection = currentPosition.copy()
        currentPosition.turnRight()
        mutableRobotPosition.value = currentPosition.copy()
    }

    private fun calculateNewPosition(currentPosition: Robot): Robot? {
        currentPosition.move()
        val newX = currentPosition.x
        val newY = currentPosition.y
        val gridSize = 5
        val isValidPosition = newX in 0 until gridSize && newY in 0 until gridSize

        return if (isValidPosition) {
            Robot(newX, newY, currentPosition.direction)
        } else {
            null
        }
    }

    private fun updateGridCell() {
        val currentPosition = mutableRobotPosition.value ?: Robot()
        gridAdapter.updateGridCell(currentPosition.y, currentPosition.x, R.drawable.ic_robot_cell)
    }
}
