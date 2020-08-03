package jmapps.mvp.content

import android.content.SharedPreferences
import android.view.ViewTreeObserver
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView

class ScrollReadPresenterImpl(
    private var sectionNumber: Int,
    private val mainScrollContainer: NestedScrollView,
    private val progressScroll: ProgressBar) : ScrollContainerContract.ScrollPresenter,
    ViewTreeObserver.OnScrollChangedListener {

    override fun scrollCount() {
        val observer: ViewTreeObserver = mainScrollContainer.viewTreeObserver
        observer.addOnScrollChangedListener(this)
    }

    override fun saveLastCount(editor: SharedPreferences.Editor) {
        editor.putInt("$sectionNumber=scrollY", mainScrollContainer.scrollY).apply()
        editor.putInt("$sectionNumber=scrollX", mainScrollContainer.scrollX).apply()
    }

    override fun loadLastCount(preferences: SharedPreferences) {
        mainScrollContainer.post {
            val scrollX = preferences.getInt("$sectionNumber=scrollX", 0)
            val scrollY = preferences.getInt("$sectionNumber=scrollY", 0)
            mainScrollContainer.scrollTo(scrollX, scrollY)
        }
    }

    override fun onScrollChanged() {
        progressScroll.max = mainScrollContainer.getChildAt(0).height - mainScrollContainer.height
        progressScroll.progress = mainScrollContainer.scrollY
    }
}