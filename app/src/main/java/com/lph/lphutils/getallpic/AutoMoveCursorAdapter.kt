package com.lph.lphutils.getallpic

import android.database.Cursor
import android.util.Log
import androidx.recyclerview.widget.RecyclerView


abstract class AutoMoveCursorAdapter<VH : RecyclerView.ViewHolder>(var mCursor: Cursor) :
    RecyclerView.Adapter<VH>() {


    override fun onBindViewHolder(holder: VH, position: Int) {
        check(isDataValid(mCursor)) { "Cannot bind view holder when cursor is in invalid state." }
        check(mCursor.moveToPosition(position)) {
            ("Could not move cursor to position " + position
                    + " when trying to bind view holder")
        }

        onBindViewHolderWithCursor(holder, mCursor)
    }


    override fun getItemCount(): Int {
        return if (isDataValid(mCursor)) {
            Log.e("getItemCount", " mCursor.getCount():" + mCursor.count)
            mCursor.count
        } else {
            0
        }
    }

    abstract fun onBindViewHolderWithCursor(holder: VH, cursor: Cursor)


    private fun isDataValid(cursor: Cursor?): Boolean {
        return cursor != null && !cursor.isClosed
    }

}