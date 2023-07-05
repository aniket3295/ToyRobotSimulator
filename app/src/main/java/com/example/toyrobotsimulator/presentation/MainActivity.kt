package com.example.toyrobotsimulator.presentation

/*To simulate the robot movement I have used grid layout of 5*5 and I have used MVVM architecture with dataBinding
* Direction class consists of all the possible directions
* Robot is the data class that also has list of methods that performs the action*/

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.toyrobotsimulator.R
import com.example.toyrobotsimulator.viewmodel.RobotViewModel
import com.example.toyrobotsimulator.adapter.GridAdapter
import com.example.toyrobotsimulator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: RobotViewModel
    private lateinit var gridAdapter: GridAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[RobotViewModel::class.java]
        binding.viewModel = viewModel

        gridAdapter = GridAdapter(5)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 5)
        binding.recyclerView.adapter = gridAdapter
        viewModel.attachGridAdapter(gridAdapter)

    }
}