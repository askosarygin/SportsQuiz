package com.example.data.network.retrofit.entities.nullable

import com.google.gson.annotations.SerializedName

data class ResponseWallpapersNullable(
    @SerializedName("record") val recordNullable: RecordNullable? = null,
    @SerializedName("metadata") val metadataNullable: MetadataNullable? = null
) {
    data class RecordNullable(
        @SerializedName("wallpapers_store") val wallpapersStoreNullable: ArrayList<WallpapersStoreNullable?>? = null
    ) {
        data class WallpapersStoreNullable(
            @SerializedName("name") val name: String? = null,
            @SerializedName("price") val price: Int? = null,
            @SerializedName("image_url") val imageUrl: String? = null
        )
    }
    data class MetadataNullable(
        @SerializedName("id") val name: String? = null,
        @SerializedName("private") val private: Boolean? = null,
        @SerializedName("createdAt") val createdAt: String? = null
    )
}