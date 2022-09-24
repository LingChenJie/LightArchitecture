package com.architecture.light.domain.transaction

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.getString
import com.architecture.light.R
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.Constant
import com.architecture.light.constant.TransactionStatus
import com.architecture.light.domain.task.*
import com.architecture.light.domain.transaction.action.*

class PrintTrans : BaseTransaction() {

    enum class State {
        SELECT_QUERY_METHOD,
        READ_ID_CARD,
        INPUT_TEL,
        SEARCH_BILL_TASK,
        CHOOSE_BILL,
        PRINT_BILL_TASK,
    }

    override fun bindStateOnAction() {
        val actionSelectQueryMethod = ActionSelectQueryMethod {
            (it as ActionSelectQueryMethod).setParam(
                currentActivity,
                getString(R.string.main_print),
                arrayOf(
                    ActionSelectQueryMethod.QueryMethod.IdCard.toString(),
                    ActionSelectQueryMethod.QueryMethod.Tel.toString(),
                )
            )
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
        val actionSearchBillTask = ActionHttpTask {
            (it as ActionHttpTask).setParam(SearchBillTask(), transData, currentActivity)
        }
        bind(State.SEARCH_BILL_TASK.name, actionSearchBillTask)
        val actionChooseBill = ActionChooseBill {
            (it as ActionChooseBill).setParam(
                currentActivity,
                transData,
                getString(R.string.main_print)
            )
        }
        bind(State.CHOOSE_BILL.name, actionChooseBill)
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
                        else -> {
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
                    gotoState(State.SEARCH_BILL_TASK.name)
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
                    gotoState(State.SEARCH_BILL_TASK.name)
                } else {
                    gotoState(State.SELECT_QUERY_METHOD.name)
                }
            }
            State.SEARCH_BILL_TASK -> {
                if (code == ErrorCode.SUCCESS) {
                    when (transData.responseCode) {
                        ErrorCode.SUCCESS -> {
                            gotoState(State.CHOOSE_BILL.name)
                        }
                        else -> {
                            toastTransResult()
                            gotoState(previousState)
                        }
                    }
                } else {
                    toastActionResult(result)
                    gotoState(previousState)
                }
            }
            State.CHOOSE_BILL -> {
                if (code == ErrorCode.SUCCESS) {
                    val info = data as ActionChooseBill.Info
                    transData.searchBillResponse = info.searchBillResponse
                    gotoState(State.PRINT_BILL_TASK.name)
                } else {
                    transEnd(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
                }
            }
            State.PRINT_BILL_TASK -> {
                if (code == ErrorCode.SUCCESS) {
                    when (transData.responseCode) {
                        ErrorCode.SUCCESS -> {
                            toastTransResult()
                            gotoState(State.CHOOSE_BILL.name)
                        }
                        else -> {
                            toastTransResult()
                            gotoState(State.CHOOSE_BILL.name)
                        }
                    }
                } else {
                    toastActionResult(result)
                    gotoState(State.CHOOSE_BILL.name)
                }
            }
        }
    }

}