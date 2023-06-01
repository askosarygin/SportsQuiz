package com.example.common


data class ResponseWallpapers(
    val record: Record,
    val metadata: Metadata
) {
    data class Record(
        val wallpapersStore: List<WallpapersStore>
    ) {
        data class WallpapersStore(
            val id: Long,
            val name: String,
            val price: Int,
            val imageUrl: String
        )
    }

    data class Metadata(
        val name: String,
        val private: Boolean,
        val createdAt: String
    )
}