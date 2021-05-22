package com.india.stockmanager

import android.net.Uri

class Model {
    var userName:String = "null"
    var title:String  = "null"
    var count:String  = "null"
    var imageURI:String = "null"

    constructor(title: String, count: String,imageURI: String,userName: String){
        this.title = title
        this.count = count
        this.imageURI = imageURI
        this.userName = userName
    }
}