package com.example.data

import android.graphics.Bitmap
import com.example.common.ResponseWallpapers
import com.example.data.account_data.AccountDataStorage
import com.example.data.network.RepositoryNetwork
import com.example.wallpapers_screen_domain.Repository
import javax.inject.Inject

class RepositoryWallpapersScreenDomainImpl @Inject constructor(
    private val accountDataStorage: AccountDataStorage,
    private val repositoryNetwork: RepositoryNetwork
) : Repository {

    override suspend fun downloadBitmapFromUrl(url: String): Bitmap =
        repositoryNetwork.downloadBitmapFromUrl(url)

    override suspend fun getPointsFromAccountDataStorage(): Int = accountDataStorage.getPoints()

    override suspend fun savePointsToAccountDataStorage(points: Int): Boolean =
        accountDataStorage.savePoints(points)

    override suspend fun loadWallpapersFromNet(): ResponseWallpapers =
        repositoryNetwork.loadWallpapersFromNet()

    override suspend fun saveBoughtWallpaperIdToAccountDataStorage(id: Long): Boolean =
        accountDataStorage.saveBoughtWallpaperId(id)

    override suspend fun checkBoughtWallpaperFromAccountDataStorage(id: Long): Boolean =
        accountDataStorage.checkBoughtWallpaper(id)
}