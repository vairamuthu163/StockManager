package com.india.stockmanager.model

import android.net.Uri

class AlphaChar {
    var iconsChar: Uri?
    var alphaChar:String  = "null"
    var alphaCount:String  = "null"

    constructor(iconsChar: Uri?, alphaChar: String, alphaCount: String){
        this.iconsChar = iconsChar
        this.alphaChar = alphaChar
        this.alphaCount = alphaCount
    }
}