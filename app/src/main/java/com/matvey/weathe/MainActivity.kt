package com.matvey.weathe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.matvey.weathe.R.*


class MainActivity : AppCompatActivity() {

    lateinit var buttonsheet:Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(layout.activity_main)

        buttonsheet = findViewById(R.id.buttonsheets)
        buttonsheet.setOnClickListener{
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.dialog_layout,null)
            dialog.setContentView(view)
            dialog.show()

        }

    }

}