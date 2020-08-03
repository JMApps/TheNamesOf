package jmapps.thenamesof.ui.main.names.model

data class MainNamesModel(
    val mainNameId: Int,
    val mainNameArabic: String,
    val mainNameTranscription: String,
    val mainNameTranslation: String,
    val mainNameAudio: String
)