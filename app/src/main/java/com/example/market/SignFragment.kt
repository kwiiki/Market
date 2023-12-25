package com.example.market

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignFragment : Fragment() {
    private val emailPattern: String
        get() = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var signInEmail: EditText
    private lateinit var signInPassword: EditText
    private lateinit var signInBtn: Button
    private lateinit var signInText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
         auth = FirebaseAuth.getInstance()
        signInEmail = view.findViewById(R.id.signInEmail)
        signInPassword = view.findViewById(R.id.signInPassword)
        signInBtn = view.findViewById(R.id.singInButton)
        signInText = view.findViewById(R.id.signInText)

        signInText.setOnClickListener {
            val loginFragment:RegistrationFragment = RegistrationFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, loginFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        signInBtn.setOnClickListener {
            val email = signInEmail.text.toString()
            val password = signInPassword.text.toString()
            if(email.isEmpty() || password.isEmpty()){
                if(email.isEmpty()){
                    signInEmail.error = "enter your email"
                }
                if(password.isEmpty()){
                    signInPassword.error = "enter your password"
                }
                Toast.makeText(requireActivity(),"Enter your email and password",Toast.LENGTH_LONG).show()
            }
            else if(!email.matches(emailPattern.toRegex())){
                signInEmail.error = "Enter your email"
                Toast.makeText(requireActivity(),"Wrong email",Toast.LENGTH_LONG).show()
            }
            else if(password.length<6){
                signInPassword.error ="password should be greater than 6 characters"
                Toast.makeText(requireActivity(),"password should be greater than 6 characters",Toast.LENGTH_LONG).show()
            }
            else{
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if(it.isSuccessful){

                        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putBoolean("user_logged_in", true)
                        editor.apply()

                        val profileFragment:ProfileFragment = ProfileFragment()
                        val transaction = requireActivity().supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.fragmentContainerView, profileFragment)
                        transaction.addToBackStack(null)
                        transaction.commit()
                    } else{
                        Toast.makeText(requireActivity(),"Please try again",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return view
    }
}