package com.example.data

import com.example.data.account_data.AccountDataStorage
import com.example.main_screen_domain.Repository
import javax.inject.Inject

class RepositoryMainScreenDomainImpl @Inject constructor(
    private val accountDataStorage: AccountDataStorage
) : Repository {
    override suspend fun getPointsFromAccountDataStorage(): Int =
        accountDataStorage.getPoints()
}