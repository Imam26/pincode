package dev.imam.converter

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

private const val PIN_CODE = "PinCode"
private const val PIN_CODE_COLOR = "PinCodeColor"

class PinCodeStatusActivity : AppCompatActivity() {
    private lateinit var textViewCode:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_code_status)

        textViewCode = findViewById(R.id.code)

        initActivityFields(savedInstanceState)
        initActivityFieldsFromIntent()
    }

    private fun initActivityFields(savedInstanceState: Bundle?){
        savedInstanceState?.getCharSequence(PIN_CODE)?.let {
            textViewCode.text = it
        }
        savedInstanceState?.getInt(PIN_CODE_COLOR)?.let {
            textViewCode.setTextColor(it)
        }
    }

    private fun initActivityFieldsFromIntent(){
        intent.getCharSequenceExtra (PIN_CODE)?.let{
            if(it != "1567"){
                textViewCode.setTextColor(resources.getColor(R.color.code_error))
            }
            textViewCode.text = it
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharSequence(PIN_CODE, textViewCode.text)
        outState.putInt(PIN_CODE_COLOR, textViewCode.currentTextColor)
    }
}