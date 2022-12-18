package com.architecture.light.domain.transaction

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.getString
import com.android.architecture.helper.AppExecutors
import com.android.architecture.helper.DateHelper
import com.android.architecture.helper.Logger
import com.android.architecture.helper.RandomHelper
import com.architecture.light.R
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.TransactionName
import com.architecture.light.constant.TransactionPlatform
import com.architecture.light.constant.TransactionStatus
import com.architecture.light.data.model.TransDataModel
import com.architecture.light.domain.task.*
import com.architecture.light.domain.transaction.action.*


class AdvancesReceivedTrans : BaseTransaction() {

    override fun onPreExecute() {
        super.onPreExecute()
        transData.transactionName = TransactionName.AdvancesReceived.name
    }

    enum class State {
        INPUT_CUSTOM_NAME,
        SEARCH_PAYMENT_TASK,
        CHOOSE_PAYMENT,
        INPUT_AMOUNT,
        CHOOSE_PAYMENT_METHOD,
        BANK_PAY_TASK,
        CODE_PAY_TASK,
        PAY_QUERY_TASK,
        SHOW_PAY_RESULT,
        NOTIFY_COLLECTION,
        SEARCH_BILL_TASK,
        PRINT_BILL_TASK,
    }

    override fun bindStateOnAction() {
        val actionInputCustomName = ActionInputCustomName {
        }
        bind(State.INPUT_CUSTOM_NAME.name, actionInputCustomName)
        val actionSearchPaymentTask = ActionHttpTask {
            (it as ActionHttpTask).setParam(SearchPaymentTask(), transData)
        }
        bind(State.SEARCH_PAYMENT_TASK.name, actionSearchPaymentTask)
        val actionChoosePayment = ActionChoosePaymentReserve {
            (it as ActionChoosePaymentReserve).setParam(
                transData,
                getString(R.string.main_advances_received)
            )
        }
        bind(State.CHOOSE_PAYMENT.name, actionChoosePayment)
        val actionInputAmount = ActionInputAmount {
            (it as ActionInputAmount).setParam(
                getString(R.string.main_advances_received)
            )
        }
        bind(State.INPUT_AMOUNT.name, actionInputAmount)
        val actionChoosePaymentMethod = ActionChoosePaymentMethod {
            (it as ActionChoosePaymentMethod).setParam(
                getString(R.string.main_advances_received),
                transData,
                false
            )
        }
        bind(State.CHOOSE_PAYMENT_METHOD.name, actionChoosePaymentMethod)
        val actionBankPayTask = ActionPayTask {
            (it as ActionPayTask).setParam(BankPayTask(), transData)
        }
        bind(State.BANK_PAY_TASK.name, actionBankPayTask)
        val actionCodePayTask = ActionPayTask {
            (it as ActionPayTask).setParam(CodePayTask(), transData)
        }
        bind(State.CODE_PAY_TASK.name, actionCodePayTask)
        val actionPayQueryTask = ActionPayTask {
            (it as ActionPayTask).setParam(PayQueryTask(), transData)
        }
        bind(State.PAY_QUERY_TASK.name, actionPayQueryTask)
        val actionShowPayResult = ActionShowPayResult {
            (it as ActionShowPayResult).setParam(
                getString(R.string.main_advances_received),
                actionResult!!,
                transData,
            )
        }
        bind(State.SHOW_PAY_RESULT.name, actionShowPayResult)
        val actionNotifyCollectionTask = ActionHttpTask {
            (it as ActionHttpTask).setParam(NotifyPrepaidTask(), transData)
        }
        bind(State.NOTIFY_COLLECTION.name, actionNotifyCollectionTask)
        val actionSearchBillTask = ActionHttpTask {
            (it as ActionHttpTask).setParam(SearchBillTask(), transData)
        }
        bind(State.SEARCH_BILL_TASK.name, actionSearchBillTask)
        val actionPrintBill = ActionPrintTask {
            (it as ActionPrintTask).setParam(PrintTask(), transData)
        }
        bind(State.PRINT_BILL_TASK.name, actionPrintBill)
        gotoState(State.INPUT_CUSTOM_NAME.name)
    }

    private var actionResult: ActionResult? = null

    override fun onActionResult(state: String, result: ActionResult) {
        this.actionResult = result
        val currentState = State.valueOf(state)
        val code = result.code
        val data = result.data
        when (currentState) {
            State.INPUT_CUSTOM_NAME -> {
                if (code == ErrorCode.SUCCESS) {
                    val info = data as ActionInputCustomName.Info
                    transData.cstName = info.cstName
                    gotoState(State.SEARCH_PAYMENT_TASK.name)
                } else {
                    transEnd(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
                }
            }
            State.SEARCH_PAYMENT_TASK -> {
                if (code == ErrorCode.SUCCESS) {
                    if (transData.responseCode == ErrorCode.SUCCESS) {
                        gotoState(State.CHOOSE_PAYMENT.name)
                    } else {
                        toastTransResult()
                        gotoState(State.INPUT_CUSTOM_NAME.name)
                    }
                } else {
                    toastActionResult(result)
                    gotoState(State.INPUT_CUSTOM_NAME.name)
                }
            }
            State.CHOOSE_PAYMENT -> {
                if (code == ErrorCode.SUCCESS) {
                    val info = data as ActionChoosePaymentReserve.Info
                    transData.searchPaymentResponse = info.searchPaymentResponse
                    gotoState(State.INPUT_AMOUNT.name)
                } else {
                    gotoState(State.INPUT_CUSTOM_NAME.name)
                }
            }
            State.INPUT_AMOUNT -> {
                if (code == ErrorCode.SUCCESS) {
                    val info = data as ActionInputAmount.Info
                    transData.amount = info.amount
                    gotoState(State.CHOOSE_PAYMENT_METHOD.name)
                } else {
                    gotoState(State.CHOOSE_PAYMENT.name)
                }
            }
            State.CHOOSE_PAYMENT_METHOD -> {
                if (code == ErrorCode.SUCCESS) {
                    val paymentMethodInfo = data as ActionChoosePaymentMethod.PaymentMethodInfo
                    transData.transactionPlatform = paymentMethodInfo.transactionPlatform
                    transData.bankAccount = paymentMethodInfo.bankAccount
                    transData.bankName = paymentMethodInfo.bankName
                    initPay()
                    if (transData.transactionPlatform == TransactionPlatform.Bank) {
                        gotoState(State.BANK_PAY_TASK.name)
                    } else {
                        gotoState(State.CODE_PAY_TASK.name)
                    }
                } else {
                    gotoState(State.INPUT_AMOUNT.name)
                }
            }
            State.BANK_PAY_TASK,
            State.CODE_PAY_TASK,
            State.PAY_QUERY_TASK -> {
                setTransactionStatusMessage()
                if (code == ErrorCode.SUCCESS) {
                    when (transData.responseCode) {
                        ErrorCode.SUCCESS -> {
                            transData.transactionStatus = TransactionStatus.TransSucceed.name
                            updateTransData()
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                        AppErrorCode.PAY_TIMEOUT -> {
                            transData.transactionStatus = TransactionStatus.TransTimeout.name
                            updateTransData()
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                        else -> {
                            transData.transactionStatus = TransactionStatus.TransFailed.name
                            updateTransData()
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                    }

                } else {
                    transData.transactionStatus = TransactionStatus.TransFailed.name
                    updateTransData()
                    gotoState(State.SHOW_PAY_RESULT.name)
                }
            }
            State.SHOW_PAY_RESULT -> {
                when (code) {
                    ErrorCode.SUCCESS -> {
                        transEnd(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
                    }
                    AppErrorCode.PAY_RESULT_QUERY -> {
                        gotoState(State.PAY_QUERY_TASK.name)
                    }
                    AppErrorCode.PAY_AGAIN -> {
                        gotoState(State.INPUT_CUSTOM_NAME.name)
                    }
                    AppErrorCode.PAY_RESULT_NOTIFY -> {
                        gotoState(State.NOTIFY_COLLECTION.name)
                    }
                    AppErrorCode.BILL_GET -> {
                        gotoState(State.SEARCH_BILL_TASK.name)
                    }
                    AppErrorCode.BILL_PRINT -> {
                        gotoState(State.PRINT_BILL_TASK.name)
                    }
                    else -> {
                        transEnd(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
                    }
                }
            }
            State.NOTIFY_COLLECTION -> {
                setTransactionStatusMessage()
                if (code == ErrorCode.SUCCESS) {
                    when (transData.responseCode) {
                        ErrorCode.SUCCESS -> {
                            transData.transactionStatus = TransactionStatus.ResultNotifySucceed.name
                            updateTransData()
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                        else -> {
                            transData.transactionStatus = TransactionStatus.ResultNotifyFailed.name
                            updateTransData()
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                    }
                } else {
                    transData.transactionStatus = TransactionStatus.ResultNotifyFailed.name
                    updateTransData()
                    gotoState(State.SHOW_PAY_RESULT.name)
                }
            }
            State.SEARCH_BILL_TASK -> {
                setTransactionStatusMessage()
                if (code == ErrorCode.SUCCESS) {
                    when (transData.responseCode) {
                        ErrorCode.SUCCESS -> {
                            transData.transactionStatus = TransactionStatus.GetPrintDataSucceed.name
                            updateTransData()
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                        else -> {
                            transData.transactionStatus = TransactionStatus.GetPrintDataFailed.name
                            updateTransData()
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                    }
                } else {
                    transData.transactionStatus = TransactionStatus.GetPrintDataFailed.name
                    updateTransData()
                    gotoState(State.SHOW_PAY_RESULT.name)
                }
            }
            State.PRINT_BILL_TASK -> {
                setTransactionStatusMessage()
                if (code == ErrorCode.SUCCESS) {
                    when (transData.responseCode) {
                        ErrorCode.SUCCESS -> {
                            transData.transactionStatus = TransactionStatus.PrintSucceed.name
                            updateTransData()
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                        else -> {
                            transData.transactionStatus = TransactionStatus.PrintFailed.name
                            updateTransData()
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                    }
                } else {
                    transData.transactionStatus = TransactionStatus.PrintFailed.name
                    updateTransData()
                    gotoState(State.SHOW_PAY_RESULT.name)
                }
            }
        }
    }

    private fun initPay() {
        val timeMillis = System.currentTimeMillis()
        val currentTime = DateHelper.getDateFormatString("yyyyMMddHHmm" + "ss", timeMillis)
        transData.transactionStatus = TransactionStatus.TransTimeout.name
        transData.transactionYear = DateHelper.yearString
        transData.transactionTimeMillis = timeMillis
        transData.orderNumber = currentTime + RandomHelper.getRandomHexString(3)
        insertTransData()
    }

    private fun setTransactionStatusMessage() {
        val code = actionResult!!.code
        if (code == ErrorCode.SUCCESS) {
            val responseCode = transData.responseCode
            val responseMessage = transData.responseMessage
            transData.transactionStatusMessage = "$responseMessage[$responseCode]"
        } else {
            val message = actionResult!!.message ?: ErrorCode.getMessage(code)
            transData.transactionStatusMessage = "$message[$code]"
        }
    }

    private fun insertTransData() {
        AppExecutors.getInstance().single().execute {
            val result = TransDataModel.insert(transData)
            Logger.e(TAG, "insertTransData result:$result")
        }
    }

    private fun updateTransData() {
        AppExecutors.getInstance().single().execute {
            val result = TransDataModel.update(transData)
            Logger.e(TAG, "updateTransData result:$result")
        }
    }


}