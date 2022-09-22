package com.architecture.light.domain.transaction

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.helper.DateHelper
import com.android.architecture.helper.RandomHelper
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.Constant
import com.architecture.light.constant.TransactionPlatform
import com.architecture.light.constant.TransactionStatus
import com.architecture.light.domain.task.*
import com.architecture.light.domain.transaction.action.*


class ReserveTrans : BaseTransaction() {

    enum class State {
        SELECT_QUERY_METHOD,
        READ_ID_CARD,
        INPUT_TEL,
        INPUT_ROOM_INFO,
        SEARCH_RESERVE_TASK,
        CHOOSE_RESERVE,
        SEARCH_PAYMENT_TASK,
        CHOOSE_PAYMENT,
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
        val actionSelectQueryMethod = ActionSelectQueryMethod {
            val queryMethodArray: Array<String> = arrayOf(
                ActionSelectQueryMethod.QueryMethod.IdCard.toString(),
                ActionSelectQueryMethod.QueryMethod.Tel.toString(),
            )
            (it as ActionSelectQueryMethod).setParam(currentActivity, queryMethodArray)
        }
        bind(State.SELECT_QUERY_METHOD.name, actionSelectQueryMethod)
        val actionReadIdCard = ActionReadIdCard {
            (it as ActionReadIdCard).setParam(currentActivity)
        }
        bind(State.READ_ID_CARD.name, actionReadIdCard)
        val actionInputTel = ActionInputTel {
            (it as ActionInputTel).setParam(currentActivity)
        }
        bind(State.INPUT_TEL.name, actionInputTel)
        val actionInputRoomInfo = ActionInputRoomInfo {
            (it as ActionInputRoomInfo).setParam(currentActivity)
        }
        bind(State.INPUT_ROOM_INFO.name, actionInputRoomInfo)
        val actionSearchReserveTask = ActionHttpTask {
            (it as ActionHttpTask).setParam(SearchReserveTask(), transData, currentActivity)
        }
        bind(State.SEARCH_RESERVE_TASK.name, actionSearchReserveTask)
        val actionChooseReserve = ActionChooseReserve {
            (it as ActionChooseReserve).setParam(currentActivity, transData)
        }
        bind(State.CHOOSE_RESERVE.name, actionChooseReserve)
        val actionSearchPaymentTask = ActionHttpTask {
            (it as ActionHttpTask).setParam(SearchPaymentTask(), transData, currentActivity)
        }
        bind(State.SEARCH_PAYMENT_TASK.name, actionSearchPaymentTask)
        val actionChoosePayment = ActionChoosePaymentReserve {
            (it as ActionChoosePaymentReserve).setParam(currentActivity, transData)
        }
        bind(State.CHOOSE_PAYMENT.name, actionChoosePayment)
        val actionChoosePaymentMethod = ActionChoosePaymentMethod {
            (it as ActionChoosePaymentMethod).setParam(currentActivity, transData, false)
        }
        bind(State.CHOOSE_PAYMENT_METHOD.name, actionChoosePaymentMethod)
        val actionBankPayTask = ActionPayTask {
            (it as ActionPayTask).setParam(BankPayTask(), transData, currentActivity)
        }
        bind(State.BANK_PAY_TASK.name, actionBankPayTask)
        val actionCodePayTask = ActionPayTask {
            (it as ActionPayTask).setParam(CodePayTask(), transData, currentActivity)
        }
        bind(State.CODE_PAY_TASK.name, actionCodePayTask)
        val actionPayQueryTask = ActionPayTask {
            (it as ActionPayTask).setParam(PayQueryTask(), transData, currentActivity)
        }
        bind(State.PAY_QUERY_TASK.name, actionPayQueryTask)
        val actionShowPayResult = ActionShowPayResult {
            (it as ActionShowPayResult).setParam(actionResult!!, transData, currentActivity)
        }
        bind(State.SHOW_PAY_RESULT.name, actionShowPayResult)
        val actionNotifyCollectionTask = ActionHttpTask {
            (it as ActionHttpTask).setParam(NotifyPrepaidTask(), transData, currentActivity)
        }
        bind(State.NOTIFY_COLLECTION.name, actionNotifyCollectionTask)
        val actionSearchBillTask = ActionHttpTask {
            (it as ActionHttpTask).setParam(SearchBillTask(), transData, currentActivity)
        }
        bind(State.SEARCH_BILL_TASK.name, actionSearchBillTask)
        val actionPrintBill = ActionPrintTask {
            (it as ActionPrintTask).setParam(PrintTask(), transData, currentActivity)
        }
        bind(State.PRINT_BILL_TASK.name, actionPrintBill)
        gotoState(State.SELECT_QUERY_METHOD.name)
    }

    private var actionResult: ActionResult? = null

    override fun onActionResult(state: String, result: ActionResult) {
        this.actionResult = result
        val currentState = State.valueOf(state)
        val code = result.code
        val data = result.data
        when (currentState) {
            State.SELECT_QUERY_METHOD -> {
                if (code == ErrorCode.SUCCESS) {
                    when (data as ActionSelectQueryMethod.QueryMethod) {
                        ActionSelectQueryMethod.QueryMethod.IdCard -> {
                            gotoState(State.READ_ID_CARD.name)
                        }
                        ActionSelectQueryMethod.QueryMethod.Tel -> {
                            gotoState(State.INPUT_TEL.name)
                        }
                        ActionSelectQueryMethod.QueryMethod.RoomInfo -> {
                            gotoState(State.INPUT_ROOM_INFO.name)
                        }
                    }
                } else {
                    transEnd(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
                }
            }
            State.READ_ID_CARD -> {
                if (code == ErrorCode.SUCCESS) {
                    val cardInfo = data as ActionReadIdCard.IdCardInfo
                    if (Constant.IS_DEBUG) {
                        transData.cardID = "999888777666"
                    } else {
                        transData.cardID = cardInfo.cardId
                    }
                    transData.tel = ""
                    transData.roomInfo = ""
                    gotoState(State.SEARCH_RESERVE_TASK.name)
                } else {
                    gotoState(State.SELECT_QUERY_METHOD.name)
                }
            }
            State.INPUT_TEL -> {
                if (code == ErrorCode.SUCCESS) {
                    val telInfo = data as ActionInputTel.TelInfo
                    if (Constant.IS_DEBUG) {
                        transData.tel = "18155723385"
                    } else {
                        transData.tel = telInfo.tel
                    }
                    transData.cardID = ""
                    transData.roomInfo = ""
                    gotoState(State.SEARCH_RESERVE_TASK.name)
                } else {
                    gotoState(State.SELECT_QUERY_METHOD.name)
                }
            }
            State.INPUT_ROOM_INFO -> {
                if (code == ErrorCode.SUCCESS) {
                    val roomInfo = data as ActionInputRoomInfo.RoomInfo
                    transData.roomInfo = roomInfo.roomInfo
                    transData.cardID = ""
                    transData.tel = ""
                    gotoState(State.SEARCH_RESERVE_TASK.name)
                } else {
                    gotoState(State.SELECT_QUERY_METHOD.name)
                }
            }
            State.SEARCH_RESERVE_TASK -> {
                if (code == ErrorCode.SUCCESS) {
                    if (transData.responseCode == ErrorCode.SUCCESS) {
                        gotoState(State.CHOOSE_RESERVE.name)
                    } else {
                        toastTransResult()
                        gotoState(previousState)
                    }
                } else {
                    toastActionResult(result)
                    gotoState(previousState)
                }
            }
            State.CHOOSE_RESERVE -> {
                if (code == ErrorCode.SUCCESS) {
                    val info = data as ActionChooseReserve.Info
                    transData.bookingGUID = info.bookingGUID
                    transData.cstName = info.cstName
                    transData.searchReserveResponse = info.searchReserveResponse
                    gotoState(State.SEARCH_PAYMENT_TASK.name)
                } else {
                    gotoState(State.SELECT_QUERY_METHOD.name)
                }
            }
            State.SEARCH_PAYMENT_TASK -> {
                if (code == ErrorCode.SUCCESS) {
                    if (transData.responseCode == ErrorCode.SUCCESS) {
                        gotoState(State.CHOOSE_PAYMENT.name)
                    } else {
                        toastTransResult()
                        gotoState(State.CHOOSE_RESERVE.name)
                    }
                } else {
                    toastActionResult(result)
                    gotoState(State.CHOOSE_RESERVE.name)
                }
            }
            State.CHOOSE_PAYMENT -> {
                if (code == ErrorCode.SUCCESS) {
                    val info = data as ActionChoosePaymentReserve.Info
                    //transData.searchReserveResponse = info.searchReserveResponse
                    gotoState(State.CHOOSE_PAYMENT_METHOD.name)
                } else {
                    gotoState(State.CHOOSE_RESERVE.name)
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
                    gotoState(State.CHOOSE_PAYMENT.name)
                }
            }
            State.BANK_PAY_TASK,
            State.CODE_PAY_TASK,
            State.PAY_QUERY_TASK -> {
                setTransactionStatusMessage()
                if (code == ErrorCode.SUCCESS) {
                    when (transData.responseCode) {
                        ErrorCode.SUCCESS -> {
                            transData.transactionStatus = TransactionStatus.PaySucceed.name
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                        AppErrorCode.PAY_TIMEOUT -> {
                            transData.transactionStatus = TransactionStatus.PayTimeout.name
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                        else -> {
                            transData.transactionStatus = TransactionStatus.PayFailed.name
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                    }

                } else {
                    transData.transactionStatus = TransactionStatus.PayFailed.name
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
                        gotoState(State.SEARCH_RESERVE_TASK.name)
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
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                        else -> {
                            transData.transactionStatus = TransactionStatus.ResultNotifyFailed.name
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                    }
                } else {
                    transData.transactionStatus = TransactionStatus.ResultNotifyFailed.name
                    gotoState(State.SHOW_PAY_RESULT.name)
                }
            }
            State.SEARCH_BILL_TASK -> {
                setTransactionStatusMessage()
                if (code == ErrorCode.SUCCESS) {
                    when (transData.responseCode) {
                        ErrorCode.SUCCESS -> {
                            transData.transactionStatus = TransactionStatus.GetPrintDataSucceed.name
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                        else -> {
                            transData.transactionStatus = TransactionStatus.GetPrintDataFailed.name
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                    }
                } else {
                    transData.transactionStatus = TransactionStatus.GetPrintDataFailed.name
                    gotoState(State.SHOW_PAY_RESULT.name)
                }
            }
            State.PRINT_BILL_TASK -> {
                setTransactionStatusMessage()
                if (code == ErrorCode.SUCCESS) {
                    when (transData.responseCode) {
                        ErrorCode.SUCCESS -> {
                            transData.transactionStatus = TransactionStatus.PrintSucceed.name
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                        else -> {
                            transData.transactionStatus = TransactionStatus.PrintFailed.name
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                    }
                } else {
                    transData.transactionStatus = TransactionStatus.PrintFailed.name
                    gotoState(State.SHOW_PAY_RESULT.name)
                }
            }
        }
    }

    private fun initPay() {
        val timeMillis = System.currentTimeMillis()
        val currentTime = DateHelper.getDateFormatString("yyyyMMddHHmm" + "ss", timeMillis)
        transData.transactionTimeMillis = timeMillis
        transData.orderNumber = currentTime + RandomHelper.getRandomHexString(3)
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


}