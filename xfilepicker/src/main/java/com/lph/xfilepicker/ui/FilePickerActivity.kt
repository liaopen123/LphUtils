package com.lph.xfilepicker.ui

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lph.xfilepicker.R
import com.lph.xfilepicker.adapter.FilePickerAdapter
import com.lph.xfilepicker.filter.LFileFilter
import com.lph.xfilepicker.utils.FileUtils
import kotlinx.android.synthetic.main.activity_file_picker.*

class FilePickerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_picker)

        val emptyView =
            LayoutInflater.from(this).inflate(R.layout.lfile_emptyview, cl_container, false)
        //如果没有指定路径，则使用默认路径
        val mPath = Environment.getExternalStorageDirectory().absolutePath
        val fileTypes: Array<String> = arrayOf()
        val mFilter =    LFileFilter(fileTypes)
       val mListFiles = FileUtils.getFileList(
           mPath,
           mFilter,
           false,
           0
       )
       var mPathAdapter = FilePickerAdapter(
           mListFiles,
           this,
           mFilter,
           false,
           false,
           0
       )
        rv_file.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
//        mPathAdapter.setmIconStyle(mParamEntity.getIconStyle())
        rv_file.adapter = mPathAdapter
        rv_file.setmEmptyView(emptyView)
    }
}