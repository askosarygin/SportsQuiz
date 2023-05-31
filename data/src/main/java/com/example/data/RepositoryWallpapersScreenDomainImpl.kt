package com.example.data

import com.example.data.account_data.AccountDataStorage
import com.example.wallpapers_screen_domain.Repository
import javax.inject.Inject

class RepositoryWallpapersScreenDomainImpl @Inject constructor(
    private val accountDataStorage: AccountDataStorage
) : Repository {

    override suspend fun getPointsFromAccountDataStorage(): Int = accountDataStorage.getPoints()

    override suspend fun savePointsToAccountDataStorage(points: Int): Boolean = accountDataStorage.savePoints(points)
}