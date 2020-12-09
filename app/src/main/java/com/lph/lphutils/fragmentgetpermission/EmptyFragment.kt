package com.lph.lphutils.fragmentgetpermission

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.fragment.app.Fragment
import com.lph.lphutils.fragmentgetpermission.utils.PermissionUtils


class EmptyFragment:Fragment() {
    private var permissionCallback: PermissionUtils.PermissionCallback? = null
    val MY_PERMISSIONS_REQUEST_READ_CONTACTS  = 888
    private val NOT_NOTICE = 2 //如果勾选了不再询问

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        myRequetPermission()
    }

    open fun myRequetPermission() {
        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        } else {
            Toast.makeText(activity!!, "您已经申请了权限!", Toast.LENGTH_SHORT).show()
        }
    }


  override  fun onRequestPermissionsResult(
      requestCode: Int,
      permissions: Array<String?>, grantResults: IntArray
  ) {

      when (requestCode) {
          MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {

              // If request is cancelled, the result arrays are empty.
              if (grantResults.size > 0
                  && grantResults[0] == PackageManager.PERMISSION_GRANTED
              ) {
                  Log.i("TAG", "onRequestPermissionsResult granted")
                  permissionCallback?.result(true,"onRequestPermissionsResult granted")
                  // permission was granted, yay! Do the
                  // contacts-related task you need to do.
              } else {
                  Log.i("TAG", "onRequestPermissionsResult denied")
                  // permission denied, boo! Disable the
                  // functionality that depends on this permission.
                  permissionCallback?.result(true,"onRequestPermissionsResult denied")
//                  showWaringDialog()

              }
              return
          }
        }
    }

    private fun showWaringDialog() {
        val dialog: AlertDialog = android.app.AlertDialog.Builder(activity!!)
            .setTitle("警告！")
            .setMessage("请前往设置->应用->PermissionDemo->权限中打开相关权限，否则功能无法正常运行！")
            .setPositiveButton(
                "确定",
                DialogInterface.OnClickListener { dialog, which -> // 一般情况下如果用户不授权的话，功能是无法运行的，做退出处理
                    activity!!.finish()
                }).show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==NOT_NOTICE){
            //用户选择了不再提示
            permissionCallback?.result(true,"onRequestPermissionsResult denied:用户选择了不再提示")
        }
    }

    fun setCallback(permissionCallback: PermissionUtils.PermissionCallback) {
        this.permissionCallback = permissionCallback
    }
}