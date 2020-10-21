package com.example.saketabi

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object Commons {
    lateinit var auth : FirebaseAuth
    var user : FirebaseUser? = null
}