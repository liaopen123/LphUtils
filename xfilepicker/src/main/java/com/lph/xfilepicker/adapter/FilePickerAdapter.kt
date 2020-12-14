package com.lph.xfilepicker.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.lph.xfilepicker.R
import com.lph.xfilepicker.utils.Constant
import com.lph.xfilepicker.utils.FileUtils
import java.io.File
import java.io.FileFilter
import java.util.*

/**
 *
 */
class FilePickerAdapter(
    var mListData: List<File>,
    var mContext: Context,
    var mFileFilter: FileFilter,
    var mMutiMode: Boolean,
    var mIsGreater: Boolean,
    var mFileSize: Long,
    private var mListNumbers: ArrayList<String> = ArrayList() //存放选中条目的数据地址

) : RecyclerView.Adapter<FilePickerAdapter.FilePickerVH>() {
    private val mIconStyle = Constant.ICON_STYLE_GREEN
    private var onItemClickListener: OnItemClickListener? = null
    private var mCheckedFlags: BooleanArray = BooleanArray(mListData.size)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilePickerVH {
        val view = View.inflate(mContext, R.layout.lfile_listitem, null)
        return FilePickerVH(view)
    }

    override fun onBindViewHolder(holder: FilePickerVH, position: Int) {
        val file = mListData[position]
        if (file.isFile) {
            updateFileIconStyle(holder.ivType)
            holder.tvName.text = file.name
            holder.tvDetail.text = "size:${FileUtils.getReadableFileSize(file.length())}"
            holder.cbChoose.visibility = View.VISIBLE
        } else {
            updateFolderIconStyle(holder.ivType)
            holder.tvName.text = file.name
            //文件大小过滤
            val files: List<*> =
                FileUtils.getFileList(file.absolutePath, mFileFilter, mIsGreater, mFileSize)
            if (files == null) {
                holder.tvDetail.text = "0 item"
            } else {
                holder.tvDetail.text = " ${files.size.toString()}item"
            }
            holder.cbChoose.visibility = View.GONE
        }
        if (!mMutiMode) {
            holder.cbChoose.visibility = View.GONE
        }
        holder.layoutRoot.setOnClickListener {
            if (file.isFile) {
                holder.cbChoose.isChecked = !holder.cbChoose.isChecked
            }
//            onItemClickListener?.click(position)
            notifyFileData(position)
        }
        holder.cbChoose.setOnClickListener { //同步复选框和外部布局点击的处理
//            onItemClickListener?.click(position)
            notifyFileData(position)
        }
        holder.cbChoose.setOnCheckedChangeListener(null) //先设置一次CheckBox的选中监听器，传入参数null

        holder.cbChoose.isChecked = mCheckedFlags[position] //用数组中的值设置CheckBox的选中状态

        //再设置一次CheckBox的选中监听器，当CheckBox的选中状态发生改变时，把改变后的状态储存在数组中
        holder.cbChoose.setOnCheckedChangeListener { compoundButton, b ->
            mCheckedFlags[position] = b
        }
    }

    override fun getItemCount() = mListData.size


    private fun updateFolderIconStyle(imageView: ImageView) {
        when (mIconStyle) {
            Constant.ICON_STYLE_BLUE -> imageView.setBackgroundResource(R.mipmap.lfile_folder_style_blue)
            Constant.ICON_STYLE_GREEN -> imageView.setBackgroundResource(R.mipmap.lfile_folder_style_green)
            Constant.ICON_STYLE_YELLOW -> imageView.setBackgroundResource(R.mipmap.lfile_folder_style_yellow)
        }
    }

    private fun updateFileIconStyle(imageView: ImageView) {
        when (mIconStyle) {
            Constant.ICON_STYLE_BLUE -> imageView.setBackgroundResource(R.mipmap.lfile_file_style_blue)
            Constant.ICON_STYLE_GREEN -> imageView.setBackgroundResource(R.mipmap.lfile_file_style_green)
            Constant.ICON_STYLE_YELLOW -> imageView.setBackgroundResource(R.mipmap.lfile_file_style_yellow)
        }
    }

    /**
     * 设置监听器
     *
     * @param onItemClickListener
     */
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    /**
     * 设置数据源
     *
     * @param mListData
     */
    fun setListData(listData: List<File>) {
        this.mListData = listData
        mCheckedFlags = BooleanArray(mListData.size)
    }


    /**
     * 设置是否全选
     *
     * @param isAllSelected
     */
    fun updateAllSelelcted(isAllSelected: Boolean) {
        for (i in mCheckedFlags.indices) {
            mCheckedFlags[i] = isAllSelected
        }
        notifyDataSetChanged()
    }


    inner class FilePickerVH(var view: View) : RecyclerView.ViewHolder(view) {
        var layoutRoot: RelativeLayout =
            view.findViewById<View>(R.id.layout_item_root) as RelativeLayout
        var ivType: ImageView = view.findViewById<View>(R.id.iv_type) as ImageView
        var tvName: TextView = view.findViewById<View>(R.id.tv_name) as TextView
        var tvDetail: TextView = view.findViewById<View>(R.id.tv_detail) as TextView
        var cbChoose: CheckBox = view.findViewById<View>(R.id.cb_choose) as CheckBox
    }

    interface OnItemClickListener {
        fun click(position: Int)
    }


    fun notifyFileData(position: Int) {
        if (mListData.get(position).isDirectory()) {
            //如果当前是目录，则进入继续查看目录
            chekInDirectory(position)
        } else {
            //如果已经选择则取消，否则添加进来

            //如果已经选择则取消，否则添加进来
            if (mListNumbers.contains(mListData[position].absolutePath)) {
                mListNumbers.remove(mListData[position].absolutePath)
            } else {
                mListNumbers.add(mListData[position].absolutePath)
            }
//            if (mParamEntity.getAddText() != null) {
//                mBtnAddBook.setText(
//                    mParamEntity.getAddText().toString() + "( " + mListNumbers.size + " )"
//                )
//            } else {
//                mBtnAddBook.setText(getString(R.string.lfile_Selected).toString() + "( " + mListNumbers.size + " )")
//            }
//            //先判断是否达到最大数量，如果数量达到上限提示，否则继续添加
//            //先判断是否达到最大数量，如果数量达到上限提示，否则继续添加
//            if (mParamEntity.getMaxNum() > 0 && mListNumbers.size > mParamEntity.getMaxNum()) {
//                Toast.makeText(this@LFilePickerActivity, R.string.lfile_OutSize, Toast.LENGTH_SHORT)
//                    .show()
//                return
//            }
        }
    }

    private fun chekInDirectory(position: Int) {
        val mPath = mListData.get(position).getAbsolutePath()
        //更新数据源
        mListData = FileUtils.getFileList(
            mPath,
            mFileFilter,
            false,
            0
        )

        notifyDataSetChanged()
    }
}