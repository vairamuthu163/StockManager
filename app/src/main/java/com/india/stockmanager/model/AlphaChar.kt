package com.india.stockmanager.model

import android.net.Uri

class AlphaChar {
    var userName:String = "null"
    var imageURI: String = "null"
    var title:String  = "null"
    var count:String  = "null"

    constructor(){

    }
    constructor(iconsChar: String, alphaChar: String, alphaCount: String,userName: String){
        this.imageURI = iconsChar
        this.title = alphaChar
        this.count= alphaCount
        this.userName = userName
    }

}