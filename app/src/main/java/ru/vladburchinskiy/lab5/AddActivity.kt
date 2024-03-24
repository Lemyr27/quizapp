package ru.vladburchinskiy.lab5

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import ru.vladburchinskiy.lab5.databinding.AddActivityBinding
import ru.vladburchinskiy.lab5.model.Post
import java.io.ByteArrayOutputStream

class AddActivity : AppCompatActivity() {

    val PICK_IMAGE_REQUEST = 1
    private lateinit var binding: AddActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AddActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
        }

        binding.imageContainer.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
        }

        binding.imageContainer.z = 1f
        binding.sendButton.z = 10f

        title = "Ваше сообщение"

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.baseline_close_24)
            setBackgroundDrawable(ColorDrawable(Color.parseColor("#222222")))
        }

        binding.nameTextField.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            binding.nameTextField.getWindowVisibleDisplayFrame(r)
            val screenHeight = binding.nameTextField.rootView.height
            val keypadHeight = screenHeight - r.bottom
            if (keypadHeight > screenHeight * 0.15) {
                binding.sendButton.translationY = -keypadHeight.toFloat()
            } else {
                binding.sendButton.translationY = 0f
            }
        }

        fun checkFieldsAndEnableButton() {
            val isTextField1Filled = binding.username.text?.isNotBlank()
            val isTextField2Filled = binding.message.text?.isNotBlank()

            if (isTextField1Filled == true && isTextField2Filled == true) {
                binding.sendButton.isEnabled = true
                binding.sendButton.setTextColor(Color.BLACK)
                binding.sendButton.bringToFront()
            } else {
                binding.sendButton.isEnabled = false
                val badYellowColor = ContextCompat.getColor(this, R.color.bad_yellow)
                binding.sendButton.setTextColor(badYellowColor)
            }
            binding.sendButton.z = 10f
        }

        binding.username.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkFieldsAndEnableButton()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.message.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkFieldsAndEnableButton()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        val db = MainDb.getDb(this)
        binding.sendButton.setOnClickListener {

            val imageBytes = if (binding.imageView.drawable != null) {
                val bitmap = (binding.imageView.drawable as BitmapDrawable).bitmap
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.toByteArray()
            } else null

            val post = Post(
                name = binding.username.text.toString(),
                message = binding.message.text.toString(),
                image = imageBytes
            )

            Thread {
                db.getDao().addPost(post)
            }.start()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            val imageView = findViewById<ImageView>(R.id.imageView)
            imageView.setImageURI(selectedImageUri)
            binding.button.visibility = View.INVISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}