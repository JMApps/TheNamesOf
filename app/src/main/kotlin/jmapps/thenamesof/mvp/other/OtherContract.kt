package jmapps.thenamesof.mvp.other

interface OtherContract {

    interface OtherView {

        fun donateProject()

        fun aboutUs()
    }

    interface OtherPresenter {
        fun downloadAllAudios()

        fun donateProject()

        fun aboutUs()

        fun rateApp()

        fun shareAppLink()
    }
}