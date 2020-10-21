package com.example.saketabi.data

import java.util.*

class LabelData {
    constructor(name : String,type : String,flavor : List<String>,comment:String,level : Int,genre:String,filePath:String,saveDate : Date){
        this.labelName = name
        this.type = type
        this.flavor = flavor
        this.comment = comment
        this.levelOfFavorite = level
        this.labelGenre = genre
        this.filePath = filePath
        this.saveDate = saveDate
    }
    var labelName : String
    var type : String
    var flavor : List<String>
    var comment : String
    var levelOfFavorite: Int = 0
    var labelGenre : String
    var filePath: String
    var saveDate : Date
}