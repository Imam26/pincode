package dev.imam.converter

import android.animation.TimeInterpolator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


private const val PIN_CODE = "PinCode"
private const val PIN_CODE_LENGTH = 4

class MainActivity : AppCompatActivity() {
    private lateinit var textViewCode:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewCode = findViewById(R.id.code)

        savedInstanceState?.getCharSequence(PIN_CODE)?.let {
            textViewCode.text = it
        }

        val btnIds = arrayOf(R.id.btn_one, R.id.btn_two, R.id.btn_three, R.id.btn_four, R.id.btn_five,
            R.id.btn_six, R.id.btn_seven, R.id.btn_eight, R.id.btn_nine, R.id.btn_zero)

        for(id in btnIds){
            val btn: Button = findViewById(id)
            btn.setOnClickListener(::onClickNumberBtn)
        }

        findViewById<Button>(R.id.btn_ok).setOnClickListener {
            onClickOkBtn()
        }

        findViewById<Button>(R.id.btn_remove).setOnClickListener {
            onClickRemoveBtn()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharSequence(PIN_CODE, textViewCode.text)
    }

    private fun onClickOkBtn(){
        if(textViewCode.length() == PIN_CODE_LENGTH) {
            Toast.makeText(this, "${textViewCode.text.toString()}", Toast.LENGTH_SHORT).show()
            runShakeAnimation(500L)
            //startPersonalPageActivity()
            //startPinCodeStatusActivity()

        } else {
            Toast.makeText(this, getString(R.string.not_correct_pincode), Toast.LENGTH_SHORT).show()
        }
    }

    private fun onClickRemoveBtn(){
        if(textViewCode.text.isNotEmpty()){
            textViewCode.text = textViewCode.text.dropLast(1)
        }
    }

    private fun onClickNumberBtn(btn: View){
        val btnText = (btn as Button).text
        if(textViewCode.length() < PIN_CODE_LENGTH){
            textViewCode.append(btnText)
        }
    }

    private fun startPinCodeStatusActivity() {
        val intent = Intent(this, PinCodeStatusActivity::class.java).apply {
            putExtra(PIN_CODE, textViewCode.text)
        }
        startActivity(intent)
    }

    private fun startPersonalPageActivity() {
        val intent = Intent(this, PersonalPageActivity::class.java)
        startActivity(intent)
    }

    private fun runShakeAnimation(duration: Long){
        val decayingSineWave = TimeInterpolator { input ->
            val raw = kotlin.math.sin(3f * input * 2 * Math.PI)
            (raw * kotlin.math.exp((-input * 2f).toDouble())).toFloat()
        }

        textViewCode.animate()
            .xBy(-100f)
            .setInterpolator(decayingSineWave)
            .setDuration(duration)
            .withStartAction { runVibration(duration) }
    }

    private fun runVibration(duration:Long){
        val vibrator = getSystemService(android.os.Vibrator::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(duration)
        }
    }
}
