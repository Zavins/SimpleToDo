package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object: TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //1. Remove the item from the list
                listOfTasks.removeAt(position)
                //2. Notify the adapter that our data set has changed.
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }

        loadItems()
        //Look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recycleView)
        //Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        //Attach adapter to recycleView
        recyclerView.adapter = adapter
        //Set the layout manager to the position of items.
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        findViewById<Button>(R.id.button).setOnClickListener{
            val userInputtedTask = inputTextField.text.toString()

            listOfTasks.add(userInputtedTask)

            adapter.notifyItemInserted(listOfTasks.size - 1)

            inputTextField.setText("")

            saveItems()
        }
    }

    //Save the data that the user has inputted
    //Save the data by writing and reading from a file

    //Get the file we need
    fun getDataFile() : File{

        //Every line is goign to represent a specific task in our ist of tasks.
        return File(filesDir, "data.txt")
    }

    //Create a method to get the file we need

    //Loading the items by reading every line in the data file
    fun loadItems(){
        try{
            listOfTasks = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

    //Save items by writing the into our data file.
    fun saveItems(){
        try{
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
        }catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }

}