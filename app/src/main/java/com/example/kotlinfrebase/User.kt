package com.example.kotlinfrebase

class User {
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var phoneNumber: String
    private lateinit var photo: String

    constructor(name: String, email: String, phoneNumber: String, photo: String) {
        this.name = name
        this.email = email
        this.phoneNumber = phoneNumber
        this.photo = photo
    }

    constructor()
    fun getName():String{
        return name
    }
    fun setName(name:String){
        this.name = name
    }

    fun getemail():String{
        return email
    }
    fun setEmail(email:String){
        this.email = email
    }

    fun getPhoneNumber():String{
        return phoneNumber
    }
    fun setPhoneNumber(phoneNumber:String){
        this.phoneNumber = phoneNumber
    }

    fun getPhoto():String{
        return photo
    }
    fun setPhoto(photo:String){
        this.photo = photo
    }

}