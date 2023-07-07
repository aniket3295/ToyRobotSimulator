package com.example.toyrobotsimulator.presentation

/*To simulate the robot movement I have used grid layout of 5*5 and I have used MVVM architecture with dataBinding
* Direction class consists of all the possible directions
* Robot is the data class that also has list of methods that performs the action*/

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    private val directionList = arrayListOf("NORTH", "SOUTH", "EAST", "WEST")
    private var selectedDirection = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[RobotViewModel::class.java]
        binding.viewModel = viewModel

        val adapterDirection = ArrayAdapter(this, android.R.layout.simple_spinner_item, directionList)
        binding.spinnerDirection.adapter = adapterDirection
        binding.spinnerDirection.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                binding.spinnerDirection.setSelection(binding.spinnerDirection.selectedItemPosition)
                selectedDirection = directionList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        binding.btnPlace.setOnClickListener{
            if(binding.xAxisEditText.text.toString().isNotEmpty() && binding.yAxisEditText.text.toString().isNotEmpty()) {
                viewModel.placeRobot(
                    binding.xAxisEditText.text.toString().toInt(),
                    binding.yAxisEditText.text.toString().toInt(),
                    selectedDirection
                )
            }else {
                Toast.makeText(this,resources.getText(R.string.error_msg),Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.robotPosition.observe(this) { robot ->
            if (robot != null)
                binding.tvPosition.text = "${robot.x}, ${robot.y}, ${robot.direction.javaClass.simpleName}"
        }

        viewModel.isValidMove.observe(this) {
            if (!it) {
                val builder = AlertDialog.Builder(this)
                    .setMessage(resources.getText(R.string.invalid_move_msg))
                    .setCancelable(false)
                    .setPositiveButton(resources.getText(R.string.ok_txt)) { dialog, which ->
                        dialog.dismiss()
                    }
                val alertDialog = builder.create()
                alertDialog.show()
            }
        }

        gridAdapter = GridAdapter(5)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 5)
        binding.recyclerView.adapter = gridAdapter
        viewModel.attachGridAdapter(gridAdapter)

    }
}