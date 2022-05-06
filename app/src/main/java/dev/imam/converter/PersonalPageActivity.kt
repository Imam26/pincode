package dev.imam.converter

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PersonalPageActivity : AppCompatActivity(R.layout.activity_personal_page) {
    private val resultActivityResult : ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ it ->
            if (it.resultCode == RESULT_OK) {
                it.data?.let { intent ->
                    val imageBitmap = intent.extras?.get("data") as Bitmap
                    personImageView.background = null
                    personImageView.setImageBitmap(imageBitmap)
                }
            }
        }

    private lateinit var personImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        personImageView = findViewById(R.id.person_image_view)

        findViewById<Button>(R.id.shareBtn).setOnClickListener {
            dispatchShareIntent()
        }

        findViewById<Button>(R.id.callBtn).setOnClickListener {
            dispatchCallIntent()
        }

        findViewById<Button>(R.id.postSendBtn).setOnClickListener {
            dispatchSendPostIntent()
        }

        findViewById<Button>(R.id.launchCameraBtn).setOnClickListener() {
            dispatchTakePictureIntent()
        }
    }

    private fun dispatchShareIntent(){
        val personsMail = findViewById<TextView>(R.id.mailTextView)?.text
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, personsMail)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, personsMail))
    }

    private fun dispatchCallIntent(){
        val callIntent = Intent(Intent.ACTION_DIAL)
        startActivity(callIntent)
    }

    private fun dispatchSendPostIntent(){
        val sendPostIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, getString(R.string.type_message))
            type = "message/rfc822"
        }
        startActivity(Intent.createChooser(sendPostIntent, getString(R.string.choose_post_app)))
    }

    private fun dispatchTakePictureIntent(){
        val takePictureIntent = Intent(ACTION_IMAGE_CAPTURE)
        try {
            resultActivityResult.launch(takePictureIntent)
        } catch (e:ActivityNotFoundException){
            showToast(getString(R.string.cam_not_found))
        }
    }

    private fun showToast(msg:String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}