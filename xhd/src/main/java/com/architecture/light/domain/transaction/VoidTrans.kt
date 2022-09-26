package com.architecture.light.domain.transaction

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.helper.AppExecutors
import com.android.architecture.helper.DateHelper
import com.android.architecture.helper.Logger
import com.android.architecture.helper.RandomHelper
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.TransactionName
import com.architecture.light.constant.TransactionPlatform
import com.architecture.light.constant.TransactionStatus
import com.architecture.light.data.model.TransDataModel
import com.architecture.light.domain.task.BankVoidTask
import com.architecture.light.domain.task.CodeVoidTask
import com.architecture.light.domain.task.NotifyVoidTask
import com.architecture.light.domain.task.VoidQueryTask
import com.architecture.light.domain.transaction.action.*
import com.architecture.light.ext.toastWarn

class VoidTrans : BaseTransaction() {

    override fun onPreExecute() {
        super.onPreExecute()
        transData.transactionName = TransactionName.Void.name
    }

    enum class State {
        INPUT_MANAGE_PWD,
        CHOOSE_ORIGIN_PAYMENT_METHOD,
        INPUT_VOUCHER_NUMBER,
        BANK_VOID_TASK,
        CODE_VOID_TASK,
        VOID_QUERY_TASK,
        SHOW_VOID_RESULT,
        NOTIFY_VOID_TASK,
    }

    override fun bindStateOnAction() {
        val actionInputManagePwd = ActionInputManagePwd {
            (it as ActionInputManagePwd).setParam(currentActivity)
        }
        bind(State.INPUT_MANAGE_PWD.name, actionInputManagePwd)
        val actionChooseOriginalPaymentMethod = ActionChooseOriginalPaymentMethod {
            (it as ActionChooseOriginalPaymentMethod).setParam(currentActivity)
        }
        bind(State.CHOOSE_ORIGIN_PAYMENT_METHOD.name, actionChooseOriginalPaymentMethod)
        val actionInputVoucherNumber = ActionInputVoucherNumber {
            (it as ActionInputVoucherNumber).setParam(currentActivity)
        }
        bind(State.INPUT_VOUCHER_NUMBER.name, actionInputVoucherNumber)
        val actionBankVoidTask = ActionPayTask {
            (it as ActionPayTask).setParam(BankVoidTask(), transData, currentActivity)
        }
        bind(State.BANK_VOID_TASK.name, actionBankVoidTask)
        val actionCodeVoidTask = ActionPayTask {
            (it as ActionPayTask).setParam(CodeVoidTask(), transData, currentActivity)
        }
        bind(State.CODE_VOID_TASK.name, actionCodeVoidTask)
        val actionVoidQueryTask = ActionPayTask {
            (it as ActionPayTask).setParam(VoidQueryTask(), transData, currentActivity)
        }
        bind(State.VOID_QUERY_TASK.name, actionVoidQueryTask)
        val actionShowVoidResult = ActionShowVoidResult {
            (it as ActionShowVoidResult).setParam(actionResult!!, transData, currentActivity)
        }
        bind(State.SHOW_VOID_RESULT.name, actionShowVoidResult)
        val actionNotifyVoidTask = ActionPayTask {
            (it as ActionPayTask).setParam(NotifyVoidTask(), transData, currentActivity)
        }
        bind(State.NOTIFY_VOID_TASK.name, actionNotifyVoidTask)
        gotoState(State.INPUT_MANAGE_PWD.name)
    }

    private var actionResult: ActionResult? = null

    override fun onActionResult(state: String, result: ActionResult) {
        this.actionResult = result
        val currentState = State.valueOf(state)
        val code = result.code
        val data = result.data
        when (currentState) {
            State.INPUT_MANAGE_PWD -> {
                if (code == ErrorCode.SUCCESS) {
                    gotoState(State.CHOOSE_ORIGIN_PAYMENT_METHOD.name)
                } else {
                    transEnd(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
                }
            }
            State.CHOOSE_ORIGIN_PAYMENT_METHOD -> {
                if (code == ErrorCode.SUCCESS) {
                    val info = data as ActionChooseOriginalPaymentMethod.PaymentMethodInfo
                    transData.transactionPlatform = info.transactionPlatform
                    gotoState(State.INPUT_VOUCHER_NUMBER.name)
                } else {
                    transEnd(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
                }
            }
            State.INPUT_VOUCHER_NUMBER -> {
                if (code == ErrorCode.SUCCESS) {
                    val info = data as ActionInputVoucherNumber.Info
                    val voucherNumber = info.voucherNumber
                    val originTrans = TransDataModel.queryByVoucher(voucherNumber)
                    if (originTrans == null) {
                        toastWarn("原交易不存在，请检查")
                        gotoState(State.INPUT_VOUCHER_NUMBER.name)
                        return
                    }
                    transData.originalVoucherNumber = voucherNumber
                    transData.originalOrderNumber = originTrans.orderNumber
                    transData.originalSerialNumber = originTrans.serialNumber
                    initPay()
                    if (transData.transactionPlatform == TransactionPlatform.Bank) {
                        gotoState(State.BANK_VOID_TASK.name)
                    } else {
                        gotoState(State.CODE_VOID_TASK.name)
                    }
                } else {
                    gotoState(State.CHOOSE_ORIGIN_PAYMENT_METHOD.name)
                }
            }
            State.BANK_VOID_TASK,
            State.CODE_VOID_TASK,
            State.VOID_QUERY_TASK -> {
                setTransactionStatusMessage()
                if (code == ErrorCode.SUCCESS) {
                    when (transData.responseCode) {
                        ErrorCode.SUCCESS -> {
                            transData.transactionStatus = TransactionStatus.TransSucceed.name
                            updateTransData()
                            gotoState(State.SHOW_VOID_RESULT.name)
                        }
                        AppErrorCode.PAY_TIMEOUT -> {
                            transData.transactionStatus = TransactionStatus.TransTimeout.name
                            updateTransData()
                            gotoState(State.SHOW_VOID_RESULT.name)
                        }
                        else -> {
                            transData.transactionStatus = TransactionStatus.TransFailed.name
                            updateTransData()
                            gotoState(State.SHOW_VOID_RESULT.name)
                        }
                    }

                } else {
                    transData.transactionStatus = TransactionStatus.TransFailed.name
                    updateTransData()
                    gotoState(State.SHOW_VOID_RESULT.name)
                }
            }
            State.SHOW_VOID_RESULT -> {
                when (code) {
                    ErrorCode.SUCCESS -> {
                        transEnd(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
                    }
                    AppErrorCode.VOID_RESULT_QUERY -> {
                        gotoState(State.VOID_QUERY_TASK.name)
                    }
                    AppErrorCode.VOID_AGAIN -> {
                        gotoState(State.CHOOSE_ORIGIN_PAYMENT_METHOD.name)
                    }
                    AppErrorCode.VOID_RESULT_NOTIFY -> {
                        gotoState(State.NOTIFY_VOID_TASK.name)
                    }
                    else -> {
                        transEnd(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
                    }
                }
            }
            State.NOTIFY_VOID_TASK -> {
                setTransactionStatusMessage()
                if (code == ErrorCode.SUCCESS) {
                    when (transData.responseCode) {
                        ErrorCode.SUCCESS -> {
                            transData.transactionStatus = TransactionStatus.ResultNotifySucceed.name
                            updateTransData()
                            gotoState(State.SHOW_VOID_RESULT.name)
                        }
                        else -> {
                            transData.transactionStatus = TransactionStatus.ResultNotifyFailed.name
                            updateTransData()
                            gotoState(State.SHOW_VOID_RESULT.name)
                        }
                    }
                } else {
                    transData.transactionStatus = TransactionStatus.ResultNotifyFailed.name
                    updateTransData()
                    gotoState(State.SHOW_VOID_RESULT.name)
                }
            }
        }
    }

    private fun initPay() {
        val timeMillis = System.currentTimeMillis()
        val currentTime = DateHelper.getDateFormatString("yyyyMMddHHmm" + "ss", timeMillis)
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