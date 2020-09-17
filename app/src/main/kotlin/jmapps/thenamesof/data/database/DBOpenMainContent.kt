package jmapps.thenamesof.data.database

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

private const val dbVersion: Int = 1

class DBOpenMainContent(context: Context?) :
    SQLiteAssetHelper(context, "MainContentDB", null, dbVersion) {
    init {
        setForcedUpgrade()
    }
}