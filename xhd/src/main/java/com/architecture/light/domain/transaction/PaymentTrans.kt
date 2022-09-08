package com.architecture.light.domain.transaction

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.Constant
import com.architecture.light.constant.TransactionPlatform
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.domain.task.SearchRoomTask
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
        BANK_PAY,
        CODE_PAY,
        SHOW_RESULT,
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
        val actionSearchRoomTask = ActionTask {
            (it as ActionTask).setParam(SearchRoomTask(), transData, currentActivity)
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
        gotoState(State.SELECT_QUERY_METHOD.name)
    }

    override fun onActionResult(state: String, result: ActionResult) {
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
                    transData.searchRoomResponse = room.searchRoomResponse
                    gotoState(State.CHOOSE_PAYMENT.name)
                } else {
                    gotoState(State.SELECT_QUERY_METHOD.name)
                }
            }
            State.CHOOSE_PAYMENT -> {
                if (code == ErrorCode.SUCCESS) {
                    val paymentInfo = data as ActionChoosePayment.PaymentInfo
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
                    if (transData.transactionPlatform == TransactionPlatform.Bank) {
                        gotoState(State.BANK_PAY.name)
                    } else {
                        gotoState(State.CODE_PAY.name)
                    }
                } else {
                    gotoState(State.CHOOSE_PAYMENT.name)
                }
            }
            State.BANK_PAY -> {
                gotoState(State.SHOW_RESULT.name)
            }
            State.CODE_PAY -> {
                gotoState(State.SHOW_RESULT.name)
            }
            State.SHOW_RESULT -> {
                if (code == ErrorCode.SUCCESS) {

                } else {

                }
            }
        }
    }

}