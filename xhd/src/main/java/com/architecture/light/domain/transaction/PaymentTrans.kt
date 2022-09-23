package com.architecture.light.domain.transaction

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.helper.AppExecutors
import com.android.architecture.helper.DateHelper
import com.android.architecture.helper.Logger
import com.android.architecture.helper.RandomHelper
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.Constant
import com.architecture.light.constant.TransactionPlatform
import com.architecture.light.constant.TransactionStatus
import com.architecture.light.data.model.TransDataModel
import com.architecture.light.domain.task.*
import com.architecture.light.domain.transaction.action.*


class PaymentTrans : BaseTransaction() {

    enum class State {
        SELECT_QUERY_METHOD,
        READ_ID_CARD,
        INPUT_TEL,
        INPUT_ROOM_INFO,
        SEARCH_ROOM_TASK,
        CHOOSE_ROOM,
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
            (it as ActionSelectQueryMethod).setParam(currentActivity)
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
        val actionSearchRoomTask = ActionHttpTask {
            (it as ActionHttpTask).setParam(SearchRoomTask(), transData, currentActivity)
        }
        bind(State.SEARCH_ROOM_TASK.name, actionSearchRoomTask)
        val actionChooseRoom = ActionChooseRoom {
            (it as ActionChooseRoom).setParam(currentActivity, transData)
        }
        bind(State.CHOOSE_ROOM.name, actionChooseRoom)
        val actionChoosePayment = ActionChoosePayment {
            (it as ActionChoosePayment).setParam(currentActivity, transData)
        }
        bind(State.CHOOSE_PAYMENT.name, actionChoosePayment)
        val actionChoosePaymentMethod = ActionChoosePaymentMethod {
            (it as ActionChoosePaymentMethod).setParam(currentActivity, transData)
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
            (it as ActionHttpTask).setParam(NotifyCollectionTask(), transData, currentActivity)
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
                        transData.cardID = "34222419890827123X"
                    } else {
                        transData.cardID = cardInfo.cardId
                    }
                    transData.tel = ""
                    transData.roomInfo = ""
                    gotoState(State.SEARCH_ROOM_TASK.name)
                } else {
                    gotoState(State.SELECT_QUERY_METHOD.name)
                }
            }
            State.INPUT_TEL -> {
                if (code == ErrorCode.SUCCESS) {
                    val telInfo = data as ActionInputTel.TelInfo
                    transData.tel = telInfo.tel
                    transData.cardID = ""
                    transData.roomInfo = ""
                    gotoState(State.SEARCH_ROOM_TASK.name)
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
                    gotoState(State.SEARCH_ROOM_TASK.name)
                } else {
                    gotoState(State.SELECT_QUERY_METHOD.name)
                }
            }
            State.SEARCH_ROOM_TASK -> {
                if (code == ErrorCode.SUCCESS) {
                    if (transData.responseCode == ErrorCode.SUCCESS) {
                        gotoState(State.CHOOSE_ROOM.name)
                    } else {
                        toastTransResult()
                        gotoState(previousState)
                    }
                } else {
                    toastActionResult(result)
                    gotoState(previousState)
                }
            }
            State.CHOOSE_ROOM -> {
                if (code == ErrorCode.SUCCESS) {
                    val room = data as ActionChooseRoom.Room
                    transData.roomGUID = room.roomGUID
                    transData.cstName = room.cstName
                    transData.searchRoomResponse = room.searchRoomResponse
                    gotoState(State.CHOOSE_PAYMENT.name)
                } else {
                    gotoState(State.SELECT_QUERY_METHOD.name)
                }
            }
            State.CHOOSE_PAYMENT -> {
                if (code == ErrorCode.SUCCESS) {
                    val paymentInfo = data as ActionChoosePayment.PaymentInfo
                    transData.totalAmount = paymentInfo.totalAmount
                    transData.unpaidAmount = paymentInfo.unpaidAmount
                    transData.amount = paymentInfo.amount
                    transData.searchRoomResponse = paymentInfo.searchRoomResponse
                    gotoState(State.CHOOSE_PAYMENT_METHOD.name)
                } else {
                    gotoState(State.CHOOSE_ROOM.name)
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
                            updateTransData()
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                        AppErrorCode.PAY_TIMEOUT -> {
                            transData.transactionStatus = TransactionStatus.PayTimeout.name
                            updateTransData()
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                        else -> {
                            transData.transactionStatus = TransactionStatus.PayFailed.name
                            updateTransData()
                            gotoState(State.SHOW_PAY_RESULT.name)
                        }
                    }

                } else {
                    transData.transactionStatus = TransactionStatus.PayFailed.name
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
                        gotoState(State.SEARCH_ROOM_TASK.name)
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