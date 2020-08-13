package jmapps.thenamesof.data.database

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

private const val dbVersion: Int = 1

class DBOpenAllNames(context: Context?) :
    SQLiteAssetHelper(context, "AllNamesDB", null, dbVersion) {
    init {
        setForcedUpgrade()
    }
}