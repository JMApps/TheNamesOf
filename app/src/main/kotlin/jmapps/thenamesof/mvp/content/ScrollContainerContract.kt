package jmapps.thenamesof.mvp.content

import android.content.SharedPreferences

class ScrollContainerContract {

    interface ScrollPresenter {

        fun scrollCount()

        fun saveLastCount(editor: SharedPreferences.Editor)

        fun loadLastCount(preferences: SharedPreferences)
    }
}