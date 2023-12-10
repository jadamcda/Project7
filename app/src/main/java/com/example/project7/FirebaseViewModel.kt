package com.example.project7

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class FirebaseViewModel : ViewModel(){
    var auth: FirebaseAuth = Firebase.auth


}