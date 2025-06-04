package com.example.agenda.app.repositories

import com.example.agenda.app.common.repositories.CRUDRepository
import com.example.agenda.domain.entities.Bank
import com.example.agenda.ui.BankFlags
import com.example.agenda.ui.screens.Banks
import kotlinx.coroutines.runBlocking

interface BankRepository : CRUDRepository<Bank> {
    fun setup() {
        _setup(
            Banks.Default.NAME_AND_ID,
            balance = Money.ZERO
        )
    }

    private fun _setup(name: String, balance: Double) {
        runBlocking {
            if (getById(name) == null) {
                create(
                    Bank(
                        name, balance,
                        id = name,
                        created_at = null,
                        updated_at = null,
                        creditLimit = null,
                        creditSpent = null,
                        flag = BankFlags.Flags.Wallet.title
                    )
                )
            }
        }
    }

    fun onlyWithCredit(): List<Bank>
}