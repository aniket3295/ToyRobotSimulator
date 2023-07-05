package com.example.toyrobotsimulator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.toyrobotsimulator.R

class GridAdapter(private val gridSize: Int) : RecyclerView.Adapter<GridAdapter.GridViewHolder>() {

    private val gridData = Array(gridSize) { Array(gridSize) { R.drawable.ic_blank_cell } }

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gridCell: ImageView = itemView.findViewById(R.id.gridCell)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_grid_cell, parent, false)
        return GridViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.gridCell.setImageResource(gridData[position / gridSize][position % gridSize])
    }

    override fun getItemCount(): Int {
        return gridSize * gridSize
    }

    fun updateGridCell(row: Int, col: Int, resourceId: Int) {
        gridData[row][col] = resourceId
        notifyItemChanged(row * gridSize + col)
    }
}
