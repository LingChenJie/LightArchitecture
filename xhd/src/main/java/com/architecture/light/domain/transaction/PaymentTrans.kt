package com.architecture.light.domain.transaction

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.domain.task.SearchRoomTask
import com.architecture.light.domain.transaction.action.*


class PaymentTrans : BaseTransaction() {

    enum class State {
        SELECT_QUERY_METHOD,
        READ_ID_CARD,
        INPUT_TEL,
        INPUT_ROOM_INFO,
        SEARCH_ROOM_TASK,
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
                    transData.cardID = cardInfo.cardId
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

                    } else {
                        toastTransResult()
                        setCurrentState(previousState)
                    }
                } else {
                    toastActionResult(result)
                    setCurrentState(previousState)
                }
            }
        }
    }

}