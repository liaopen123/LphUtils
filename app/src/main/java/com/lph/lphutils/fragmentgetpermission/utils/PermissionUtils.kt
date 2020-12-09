package com.lph.lphutils.fragmentgetpermission.utils

import android.Manifest
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.lph.lphutils.fragmentgetpermission.EmptyFragment

class PermissionUtils(var activity:AppCompatActivity) {

     val emptyFragment by lazy {
         EmptyFragment()
     }
    fun request( vararg permission:String): PermissionUtils {
        val transaction = activity.supportFragmentManager.beginTransaction()

        transaction.add(emptyFragment,"gaga")
        transaction.commit()


        return this
    }
    fun setCallback(permissionCallback: PermissionCallback){
        emptyFragment.setCallback(permissionCallback)
    }


    interface  PermissionCallback{
        fun result(isSuccess:Boolean,message:String);
    }

}