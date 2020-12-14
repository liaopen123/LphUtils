package com.lph.lphutils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lph.xfilepicker.ui.FilePickerActivity

class FilePickerDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_picker_demo)
        startActivity(Intent(this, FilePickerActivity::class.java))

    }
}