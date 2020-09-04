package jmapps.thenamesof.mvp.other

import android.content.Context
import android.content.Intent
import android.net.Uri

class OtherPresenterImpl(
    private val context: Context?,
    private val otherView: OtherContract.OtherView?) : OtherContract.OtherPresenter {

    private val downloadLink = "https://drive.google.com/uc?export=download&id=15CJJgenAE29MByDshbhnn_PgOrnVoVxZ"
    private val appLink = "https://play.google.com/store/apps/details?id=jmapps.thenamesof"

    override fun darkTheme(themeMode: Boolean) {
        otherView?.darkTheme(themeMode)
    }

    override fun downloadAllAudios() {
        val downloadAudios = Intent(Intent.ACTION_VIEW)
        downloadAudios.data = Uri.parse(downloadLink)
        context?.startActivity(downloadAudios)
    }

    override fun donateProject() {
        otherView?.donateProject()
    }

    override fun aboutUs() {
        otherView?.aboutUs()
    }

    override fun rateApp() {
        val rateApp = Intent(Intent.ACTION_VIEW)
        rateApp.data = Uri.parse(appLink)
        context?.startActivity(rateApp)
    }

    override fun shareAppLink() {
        val shareLink = Intent(Intent.ACTION_SEND)
        shareLink.type = "text/plain"
        shareLink.putExtra(Intent.EXTRA_TEXT, "Толкование прекрасных имён Аллаха\n$appLink").toString()
        context?.startActivity(shareLink)
    }
}