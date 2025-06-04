package com.example.agenda.ui.component.transactions.createBottomSheet

import Money
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.domain.entities.Bank
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.BottomSheet
import com.example.agenda.ui.component.CreateBankBottomSheet
import com.example.agenda.ui.component.Form
import com.example.agenda.ui.component.MultiStep
import com.example.agenda.ui.screens.Transactions
import com.example.agenda.ui.viewmodels.TransactionsVM
import kotlinx.coroutines.runBlocking

@Composable
fun CreateTransactionBottomSheet(): () -> Unit {


    val toggleCreateBankBottomSheet = CreateBankBottomSheet()

    val vm: TransactionsVM = viewModel()
    var bank by remember { mutableStateOf<Bank?>(null) }
    var banks by remember { mutableStateOf<List<Bank>>(listOf()) }


    var type by remember { mutableStateOf<Transactions.Type?>(null) }

    var bankTransferTo by remember { mutableStateOf<Bank?>(null) }

    var bankTransferToCurrency by remember { mutableStateOf(Money.resolve(Money.ZERO)) }
    var bankTransferToCurrencyInput by remember {
        mutableStateOf(
            TextFieldValue(
                text = bankTransferToCurrency.text,
                selection = TextRange(bankTransferToCurrency.text.length)
            )
        )
    }

    fun create() {

    }

    val balanceType = remember { mutableStateOf(Transactions.BalanceType.DEBIT) }
    val toggleBottomSheet = BottomSheet.Wrapper { toggle ->
        MultiStep.Wrapper(
            { create();toggle() }, steps = { next, previous, index ->
                listOf(
                    MultiStep.StepData(
                        content = {
                            MultiStep.Step("Forma de pagamento", previous, index) {
                                MultiStep.List(
                                    listOf(
                                        MultiStep.ItemData(
                                            name = Transactions.BalanceType.DEBIT.value,
                                            callback = {
                                                balanceType.value = Transactions.BalanceType.DEBIT
                                                runBlocking {
                                                    //banks = vm.getBanks(false)
                                                }
                                            }
                                        ),
                                        MultiStep.ItemData(
                                            name = Transactions.BalanceType.CREDIT.value,
                                            callback = {
                                                balanceType.value = Transactions.BalanceType.CREDIT
                                                runBlocking {
                                                    //banks = vm.getBanks(true)
                                                }
                                            }
                                        )
                                    ),
                                ) {
                                    MultiStep.Item(it, next)
                                }
                            }
                        }),
                    MultiStep.StepData(content = {
                        MultiStep.Step("Selecionar o banco", previous, index) {
                            MultiStep.List(banks, fallback = {
                                BTN(
                                    "Criar Banco",
                                    toggleCreateBankBottomSheet,
                                    modifier = Modifier.absoluteOffset(1.dp, 1.dp)
                                )
                            }) {
                                BankCard(it, callback = { bank = it;next() })
                            }
                        }
                    }),
                    MultiStep.StepData(content = {
                        MultiStep.Step("Selecionar tipo de transação", previous, index) {
                            MultiStep.List(
                                listOf(
                                    MultiStep.ItemData(
                                        name = Transactions.Type.EXPENSE.value,
                                        callback = {
                                            type = Transactions.Type.EXPENSE
                                            next()
                                        }
                                    ),
                                    MultiStep.ItemData(
                                        name = Transactions.Type.INCOME.value,
                                        callback = {
                                            type = Transactions.Type.INCOME
                                            next()
                                        }
                                    ),
                                    MultiStep.ItemData(
                                        name = Transactions.Type.TRANSFER.value,
                                        callback = {
                                            type = Transactions.Type.TRANSFER
                                            next()
                                        }
                                    )
                                )) {
                                MultiStep.Item(it, next)
                            }
                        }
                    }),
                    MultiStep.StepData(
                        isAvailable = type == Transactions.Type.TRANSFER,
                        content = {
                            MultiStep.Step("Para que banco", previous, index) {
                                MultiStep.List(
                                    banks.filter { it.id != bank?.id },
                                ) {
                                    BankCard(it, callback = { bankTransferTo = it;next() })
                                }
                            }
                        }),
                    MultiStep.StepData(
                        isAvailable = type == Transactions.Type.TRANSFER,
                        content = {
                            MultiStep.Step("Qual valor", previous, index) {
                                Form.Input.Money(
                                    icon = Theme.Icons.Money.icon,
                                    placeholder = "Valor",
                                    value = bankTransferToCurrencyInput,
                                    onValueChange = {
                                        bankTransferToCurrency = Money.resolve(it)
                                        bankTransferToCurrencyInput =
                                            bankTransferToCurrencyInput.copy(
                                                text = bankTransferToCurrency.text,
                                                selection = TextRange(bankTransferToCurrency.text.length)
                                            )
                                    })
                            }
                        }),
                    MultiStep.StepData(content = {
                        MultiStep.Step("Finalizar", previous, index) {
                        }
                    })
                )
            })
    }
    return toggleBottomSheet
}