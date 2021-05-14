package com.india.stockmanager.model

import android.net.Uri

class AlphaChar {
    var imageURI: String
    var title:String  = "null"
    var count:String  = "null"

    constructor(iconsChar: String, alphaChar: String, alphaCount: String){
        this.imageURI = iconsChar
        this.title = alphaChar
        this.count= alphaCount
    }
}