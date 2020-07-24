package jmapps.thenamesof.data.database

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import jmapps.thenamesof.ui.content.model.ModelContent

class MainContentList(private val database: SQLiteDatabase) {

    val getContentList: MutableList<ModelContent>

        @SuppressLint("Recycle")
        get() {
            val cursor: Cursor = database.query(
                "Table_of_content",
                null,
                null,
                null,
                null,
                null,
                null
            )

            val contentList = ArrayList<ModelContent>()

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val contents = ModelContent(
                        cursor.getInt(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex("ContentNumber")),
                        cursor.getString(cursor.getColumnIndex("ContentTitle")),
                        cursor.getString(cursor.getColumnIndex("ContentList"))
                    )
                    contentList.add(contents)
                    cursor.moveToNext()
                    if (cursor.isClosed) {
                        cursor.close()
                    }
                }
            }
            return contentList
        }
}