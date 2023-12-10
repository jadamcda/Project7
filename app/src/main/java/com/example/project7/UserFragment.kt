package com.example.project7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class UserFragment : Fragment() {
    lateinit var viewModel: FirebaseViewModel

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        viewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)

        val backButton = view.findViewById<Button>(R.id.backButton)
        val welcomeText = view.findViewById<TextView>(R.id.welcomeText)
        val emailEditText = view.findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = view.findViewById<EditText>(R.id.passwordEditText)
        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val signUpButton = view.findViewById<Button>(R.id.signUpButton)
        val signOutButton = view.findViewById<Button>(R.id.signOutButton)

        backButton.setOnClickListener {
            view.findNavController().navigate(UserFragmentDirections.actionUserFragmentToHomeFragment("", ""))
        }

        val currentUser = viewModel.auth.currentUser
        if (currentUser != null) {
            welcomeText.text = "Do you want to sign out?"
            emailEditText.visibility = View.INVISIBLE
            passwordEditText.visibility = View.INVISIBLE
            loginButton.visibility = View.INVISIBLE
            signUpButton.visibility = View.INVISIBLE
            signOutButton.visibility = View.VISIBLE

            signOutButton.setOnClickListener {
                // sign out
                Firebase.auth.signOut()
                Toast.makeText(activity, "Signed out", Toast.LENGTH_LONG).show()
                println("User: " + viewModel.auth.currentUser?.email)

                welcomeText.text = "Welcome"
                emailEditText.visibility = View.VISIBLE
                passwordEditText.visibility = View.VISIBLE
                loginButton.visibility = View.VISIBLE
                signUpButton.visibility = View.VISIBLE
                signOutButton.visibility = View.INVISIBLE
            }
        }
        else{
            welcomeText.text = "Welcome"
            emailEditText.visibility = View.VISIBLE
            passwordEditText.visibility = View.VISIBLE
            loginButton.visibility = View.VISIBLE
            signUpButton.visibility = View.VISIBLE
            signOutButton.visibility = View.INVISIBLE



            loginButton.setOnClickListener {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                Firebase.auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    Toast.makeText(activity, "You're logged in!", Toast.LENGTH_LONG).show()
                    println("User: " + viewModel.auth.currentUser?.email)
                    view.findNavController().navigate(UserFragmentDirections.actionUserFragmentToHomeFragment("", ""))
                }.addOnFailureListener {
                    Toast.makeText(activity, "Login failure", Toast.LENGTH_LONG).show()
                }
            }

            signUpButton.setOnClickListener {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                Firebase.auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                    Toast.makeText(activity, "You're signed up!", Toast.LENGTH_LONG).show()
                    view.findNavController().navigate(UserFragmentDirections.actionUserFragmentToHomeFragment("", ""))
                }.addOnFailureListener {
                    Toast.makeText(activity, "Sign up failure", Toast.LENGTH_LONG).show()
                }

                println(viewModel.auth.currentUser?.email)
            }
        }

        return view
    }
}