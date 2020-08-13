package jmapps.thenamesof.data.database

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import jmapps.thenamesof.ui.allnames.model.AllNamesModel
import jmapps.thenamesof.ui.flip.model.FlipListModel

class AllNamesContent(private val database: SQLiteDatabase) {

    val getAllNamesList: MutableList<AllNamesModel>
        @SuppressLint("Recycle")
        get() {
            val cursor: Cursor = database.query(
                "Table_of_all_names",
                null,
                null,
                null,
                null,
                null,
                null
            )

            val allNameList = ArrayList<AllNamesModel>()

            try {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast) {
                        val allNames = AllNamesModel(
                            cursor.getInt(cursor.getColumnIndex("_id")),
                            cursor.getString(cursor.getColumnIndex("NameArabic")),
                            cursor.getString(cursor.getColumnIndex("NameTranscription")),
                            cursor.getString(cursor.getColumnIndex("NameTranslation")),
                            cursor.getString(cursor.getColumnIndex("NameAudio"))
                        )
                        allNameList.add(allNames)
                        cursor.moveToNext()
                        if (cursor.isClosed) {
                            cursor.close()
                        }
                    }
                }
            } catch (e: SQLException) {

            }
            return allNameList
        }

    val getFlipNamesList: MutableList<FlipListModel>
        @SuppressLint("Recycle")
        get() {
            val cursor: Cursor = database.query(
                "Table_of_flip_names",
                null,
                null,
                null,
                null,
                null,
                null
            )

            val flipNameList = ArrayList<FlipListModel>()

            try {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast) {
                        val flips = FlipListModel(
                            cursor.getInt(cursor.getColumnIndex("_id")),
                            cursor.getString(cursor.getColumnIndex("NameArabic")),
                            cursor.getString(cursor.getColumnIndex("NameTranslation")),
                            cursor.getString(cursor.getColumnIndex("NameAudio"))
                        )
                        flipNameList.add(flips)
                        cursor.moveToNext()
                        if (cursor.isClosed) {
                            cursor.close()
                        }
                    }
                }
            } catch (e: SQLException) {

            }
            return flipNameList
        }
}