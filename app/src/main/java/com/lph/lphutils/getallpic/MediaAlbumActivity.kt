package com.lph.lphutils.getallpic

import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.lph.lphutils.R
import kotlinx.android.synthetic.main.activity_media_album.*
import java.io.File

class MediaAlbumActivity : AppCompatActivity() {
    var mediaBeen: ArrayList<MediaBean> = ArrayList()
    var allPhotosTemp: HashMap<String, ArrayList<MediaBean>> = HashMap() //所有照片


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_album)
        rv.layoutManager = GridLayoutManager(this, 3)
        getAllPhotoInfo()
    }

    private fun getAllPhotoInfo() {
        val mImageUri  = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        var proImage = arrayOf<String>(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DISPLAY_NAME
        )

        var mCursor = contentResolver.query(
            mImageUri,
            proImage,
            MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
            arrayOf<String>("image/jpeg", "image/png"),
            MediaStore.Images.Media.DATE_MODIFIED + " desc"
        )

        rv.adapter = RVAdapter(mCursor!!,this)

//        if (mCursor!=null) {
//            while (mCursor.moveToNext()){
//                // 获取图片的路径
//                // 获取图片的路径
//                val path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA))
//                val size =
//                    mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.SIZE)) / 1024
//                val displayName =
//                    mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))
//                //用于展示相册初始化界面
//                //用于展示相册初始化界面
//                val mediaBean = MediaBean(path, size, displayName)
//                Log.e("mediaBean","mediaBean:$mediaBean")
//                mediaBeen.add(MediaBean( path, size, displayName))
//                // 获取该图片的父路径名
//                // 获取该图片的父路径名
//                val dirPath: String = File(path).getParentFile().getAbsolutePath()
//                //存储对应关系
//                //存储对应关系
//                if (allPhotosTemp.containsKey(dirPath)) {
//                    val data = allPhotosTemp.get(dirPath)
//                    data!!.add(MediaBean(path, size, displayName))
//                    continue
//                } else {
//                    val data: ArrayList<MediaBean> = ArrayList()
//                    data.add(MediaBean( path, size, displayName))
//                    allPhotosTemp.put(dirPath, data)
//                }
//
//            }
//
//        }



    }
}