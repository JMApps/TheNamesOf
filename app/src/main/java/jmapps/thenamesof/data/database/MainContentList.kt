package jmapps.thenamesof.data.database

import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import jmapps.thenamesof.ui.main.names.model.MainNamesModel

class MainContentList(private val database: SQLiteDatabase) {

    fun getMainNamesList(sortedBy: Int?): MutableList<MainNamesModel> {

        val cursor: Cursor = database.query(
            "Table_of_names",
            null,
            "SortedBy = $sortedBy",
            null,
            null,
            null,
            null
        )

        val mainNames = ArrayList<MainNamesModel>()

        try {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val names = MainNamesModel(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("NameArabic")),
                        cursor.getString(cursor.getColumnIndex("NameTranscription")),
                        cursor.getString(cursor.getColumnIndex("NameTranslation")),
                        cursor.getString(cursor.getColumnIndex("NameAudio"))
                    )
                    mainNames.add(names)
                    cursor.moveToNext()
                    if (cursor.isClosed) {
                        cursor.close()
                    }
                }
            }
        } catch (e: SQLException) {

        }
        return mainNames
    }
}