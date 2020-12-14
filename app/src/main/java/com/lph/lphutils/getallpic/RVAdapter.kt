package com.lph.lphutils.getallpic

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lph.lphutils.R

class RVAdapter(var cursor: Cursor,var context:Context):AutoMoveCursorAdapter<RVAdapter.ImageViewHolder>(cursor) {










    override fun onBindViewHolderWithCursor(holder: ImageViewHolder, cursor: Cursor) {
        var uri = ContentUris.withAppendedId(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getLong(
                cursor.getColumnIndex(
                    MediaStore.Files.FileColumns._ID
                )
            )
        )


        Glide.with(context).load(uri).into(holder.iv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.photo, parent, false)
        return ImageViewHolder(v)
    }


    inner class ImageViewHolder(val itemView: View):RecyclerView.ViewHolder(itemView){
        val iv = itemView.findViewById<ImageView>(R.id.iv)


    }
}