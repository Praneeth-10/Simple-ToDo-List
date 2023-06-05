package com.example.todolist

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    var listItem = ArrayList<String>()

    var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root

        setContentView(view)

        listItem = fileHelper.readData(this@MainActivity)
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,listItem)

        mainBinding.displayData.adapter = arrayAdapter

        mainBinding.buttonAdd.setOnClickListener {
            listItem.add(mainBinding.textToAdd.text.toString())
            fileHelper.writeData(listItem,this@MainActivity)
            mainBinding.textToAdd.setText("")
            arrayAdapter.notifyDataSetChanged()
        }

        mainBinding.displayData.setOnItemClickListener { adapterView, view, position, l ->

            val alert = AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setCancelable(false)
            alert.setMessage("Do you want to mark it as complete?")
            alert.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })
            alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                listItem.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.writeData(listItem,this@MainActivity)
            })
            alert.create()
            alert.show()

        }


    }
}