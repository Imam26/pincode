package dev.imam.converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var textViewCode:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewCode = findViewById(dev.imam.converter.R.id.code)

        val btnIds = arrayOf(R.id.btn_one, R.id.btn_two, R.id.btn_three, R.id.btn_four, R.id.btn_five,
            R.id.btn_six, R.id.btn_seven, R.id.btn_eight, R.id.btn_nine, R.id.btn_zero, R.id.btn_remove,
            R.id.btn_ok)

        for(id in btnIds){
            val btn: Button = findViewById(id)
            btn.setOnClickListener(::onClickBtn)
        }
    }

    private fun onClickBtn(btn: View){
        val btnText = (btn as Button).text
        val str = textViewCode.text.toString()
        val length = textViewCode.length()

        when(btnText){
            "OK" ->  if(length == 4) {
                Toast.makeText(this, "$str", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Не полный пин код", Toast.LENGTH_SHORT).show()
            }
            "<" -> if(length > 0){
                textViewCode.text = str.subSequence(0, length - 1)
            }
            else -> if(length < 4){
                textViewCode.text = str + btnText
            }
        }
    }
}