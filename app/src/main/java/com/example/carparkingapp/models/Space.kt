package com.example.carparkingapp.models

class Space {
    var isEmpty: Boolean = true
    var startTime: String = ""
    var endTime: String = ""
    var username: String = ""

    constructor()
    constructor(isEmpty: Boolean, startTime: String, endTime: String, username: String) {
        this.isEmpty = isEmpty
        this.startTime = startTime
        this.endTime = endTime
        this.username = username
    }


    fun getIsEmpty(): Boolean {
        return isEmpty
    }

    fun setIsEmpty(isEmpty: Boolean) {
        this.isEmpty = isEmpty
    }

    fun getOrderStartTime(): String {
        return startTime
    }

    fun setOrderStartTime(startTime: String) {
        this.startTime = startTime
    }

    fun getOrderEndTime(): String {
        return endTime
    }

    fun setOrderEndTime(endTime: String) {
        this.endTime = endTime
    }

    fun getOrderUsername(): String {
        return username
    }

    fun setOrderUsername(username: String) {
        this.username = username
    }

}