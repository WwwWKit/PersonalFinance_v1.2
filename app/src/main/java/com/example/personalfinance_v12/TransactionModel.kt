package com.example.personalfinance_v12

import android.text.Editable
import java.util.*

class TransactionModel {
    var transactionID: String? = null
    var type: Int? = null
    var title: String? = null
    var category: String? = null
    var amount: Double? = null
    var date: Long? = null
    var note: String? = null
    var invertedDate: Long? = null
    var uid: String? = null

    constructor(){

    }
    constructor(
        transactionID: String?,
        type: Int?,
        title: String?,
        category: String?,
        amount: Double?,
        date: Long?,
        note: String?,
        invertedDate: Long?,
        uid : String?
    ) {
        this.transactionID = transactionID
        this.type = type
        this.title = title
        this.category = category
        this.amount = amount
        this.date = date
        this.note = note
        this.invertedDate = invertedDate
        this.uid = uid

    }

}