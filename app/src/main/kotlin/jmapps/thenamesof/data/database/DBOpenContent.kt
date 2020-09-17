package jmapps.thenamesof.data.database

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

private const val dbVersion: Int = 1

class DBOpenContent(context: Context?) :
    SQLiteAssetHelper(context, "ContentDB", null, dbVersion) {
    init {
        setForcedUpgrade()
    }
}