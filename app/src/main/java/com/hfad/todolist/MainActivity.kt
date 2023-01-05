package com.hfad.todolist

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    lateinit var item : EditText
    lateinit var add : Button
    lateinit var listview : ListView
    var fileHelper= FileHelper()
    var itemList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        item = findViewById(R.id.editText)
        add = findViewById(R.id.button)
        listview = findViewById(R.id.list)
        itemList = fileHelper.ReadData(this)
        var arrayAdapter = ArrayAdapter(this,android.R.layout
            .simple_list_item_1,android.R.id.text1,itemList)
    listview.adapter = arrayAdapter
        add.setOnClickListener {
            var itemName:String = item.text.toString()
            itemList.add(itemName)
            item.setText("")
            fileHelper.writeData(itemList,applicationContext)
            arrayAdapter.notifyDataSetChanged()

        }
        listview.setOnItemClickListener { parent, view, position, id ->
            var alert = AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setMessage("DO you want to delete this item from the list?")
            alert.setCancelable(false)
            alert.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            alert.setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, which ->
               itemList.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.writeData(itemList,applicationContext)
            })
            alert.create().show()
        }
    }
}