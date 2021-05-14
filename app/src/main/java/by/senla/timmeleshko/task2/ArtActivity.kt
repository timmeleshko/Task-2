package by.senla.timmeleshko.task2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class ArtActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_art)
    }

    fun goToImageViewActivity(view: View) {
        val intent = Intent(this, ImageViewActivity::class.java)
        startActivity(intent)
    }
}