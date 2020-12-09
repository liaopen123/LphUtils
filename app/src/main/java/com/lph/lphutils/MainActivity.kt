package com.lph.lphutils

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lph.lphutils.databinding.ActivityMainBinding
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val REQUEST_CODE_CHOOSE = 888

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var  filePaths = ArrayList<Uri>()
        binding.btnExplore.setOnClickListener {
            FilePickerBuilder.instance
                .setMaxCount(5) //optional
                .setSelectedFiles(filePaths) //optional
                .setActivityTheme(R.style.LibAppTheme) //optional
                .pickPhoto(this);
        }

        val okHttpClient = OkHttpClient()

        //创建post请求数据表单
        //创建post请求数据表单
        val requestBody: RequestBody = FormBody.Builder()
                .add("name", "请求post")
                .add("password", "123456")
                .build()
        val request = Request.Builder().url("http://www.sosoapi.com/pass/mock/12003/test/posttest").post(requestBody).build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("TAG", response.body().toString())
            }
        })
    }



     override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
         when (requestCode) {
             FilePickerConst.REQUEST_CODE_PHOTO -> if (resultCode === RESULT_OK && android.R.attr.data != null) {
                 var  photoPaths = ArrayList<Uri>()
                 photoPaths.addAll(
                     data!!.getParcelableArrayListExtra<Uri>(
                         FilePickerConst.KEY_SELECTED_MEDIA
                     )!!
                 ) as ArrayList<Uri>
             }
             FilePickerConst.REQUEST_CODE_DOC -> if (resultCode === RESULT_OK && android.R.attr.data != null) {
                 var docPaths = ArrayList<Uri>()
                 docPaths.addAll(
                     data!!.getParcelableArrayListExtra<Uri>(
                         FilePickerConst.KEY_SELECTED_DOCS
                     )!!
                 )  as ArrayList<Uri>
             }
         }
    }


}