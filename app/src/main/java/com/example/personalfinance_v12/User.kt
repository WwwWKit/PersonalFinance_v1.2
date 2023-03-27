package com.example.personalfinance_v11

class User {
    // val is immutable, var is mutable
    var name:String? = null
    var email:String? = null
    var uid:String? = null

    constructor(){

    }

    constructor(name:String?,email:String?,uid:String?){
        this.name = name
        this.email = email
        this.uid = uid
    }
}