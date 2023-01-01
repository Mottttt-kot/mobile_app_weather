package com.matvey.weathe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.widget.Button

class initial_activity : AppCompatActivity() {
    lateinit var buttonnext :Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)

        //checkPermission()
        buttonnext = findViewById(R.id.button)
        buttonnext.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                ActivityCompat.requestPermissions(this@initial_activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
            }

        })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1 && permissions.isNotEmpty())
        {
            val  intent = Intent(this,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

//    private fun checkPermission(){
//        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//        {
//
//            MaterialAlertDialogBuilder(this).setTitle("Приветственное окно").
//            setMessage("Это первый запуск приложения, Author: Matvey Volodkin, email:matvolodkin@gmail.com ").
//            setPositiveButton("Ok")
//            {
//                    _,_ -> ActivityCompat.requestPermissions(
//                this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                1
//            )
//                ActivityCompat.requestPermissions(
//                    this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),1
//                )
//            }
//                .setNegativeButton("Cancel")
//                {
//                        dialog,_ ->dialog.dismiss()
//                }
//                .create()
//                .show()
//        }
//
//    }


}