package com.lph.lphutils.fragmentgetpermission.utils

import android.Manifest
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.lph.lphutils.fragmentgetpermission.EmptyFragment
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    /**
     * 回调的写法 就是用协程包裹回调  异步就能改成同步的了
     */
    suspend fun getResult():String{
        return suspendCoroutine {
         try {
             emptyFragment.setCallback(object :PermissionCallback{
                 override fun result(isSuccess: Boolean, message: String) {
                     it.resume(message)
                 }

             })
         }catch (e:Exception){
             it.resumeWithException(e)
         }
        }
    }


    interface  PermissionCallback{
        fun result(isSuccess:Boolean,message:String);
    }

}