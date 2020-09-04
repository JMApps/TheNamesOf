package jmapps.thenamesof.mvp.other

interface OtherContract {

    interface OtherView {

        fun darkTheme(themeMode: Boolean)

        fun donateProject()

        fun aboutUs()
    }

    interface OtherPresenter {

        fun darkTheme(themeMode: Boolean)

        fun downloadAllAudios()

        fun donateProject()

        fun aboutUs()

        fun rateApp()

        fun shareAppLink()
    }
}