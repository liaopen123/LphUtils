package com.lph.lphutils.fragmentgetpermission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.lph.lphutils.R
import com.lph.lphutils.databinding.ActivityPermission1Binding
import com.lph.lphutils.fragmentgetpermission.utils.PermissionUtils

class Permission1Activity : AppCompatActivity() {
    val mContext = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPermission1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGet.setOnClickListener {
            PermissionUtils(this).request().setCallback(object :PermissionUtils.PermissionCallback{
                override fun result(isSuccess: Boolean, message: String) {
                    Toast.makeText(mContext,"result:$isSuccess,$message",Toast.LENGTH_LONG).show()
                }

            })

        }

    }
}