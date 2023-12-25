package com.example.market

import android.content.Context
import android.os.Bundle
import android.util.Log
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

class RegistrationFragment : Fragment() {
    private lateinit var signUpName:EditText
    private lateinit var signUpEmail:EditText
    private lateinit var signUpPhone:EditText
    private lateinit var signUpPassword:EditText
    private lateinit var signUpCPassword:EditText
    private lateinit var signUpBtn:Button
    private lateinit var signUpText:TextView
    private val emailPattern: String
        get() = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var auth: FirebaseAuth
    private lateinit var database:FirebaseDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        signUpName = view.findViewById(R.id.signUpName)
        signUpEmail = view.findViewById(R.id.signUpEmail)
        signUpPhone = view.findViewById(R.id.signUpPhone)
        signUpPassword = view.findViewById(R.id.signUpPassword)
        signUpCPassword = view.findViewById(R.id.signUpsPassword)
        signUpBtn = view.findViewById(R.id.singUpButton)
        signUpText = view.findViewById(R.id.signUpText)

        auth = FirebaseAuth.getInstance()
        signUpText.setOnClickListener {

            val singUpFragment:SignFragment = SignFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, singUpFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        signUpBtn.setOnClickListener {
            val name = signUpName.text.toString()
            val email = signUpEmail.text.toString()
            val phone = signUpPhone.text.toString()
            val password = signUpPassword.text.toString()
            val passwordc = signUpCPassword.text.toString()

            if(name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || passwordc.isEmpty()){
                if(name.isEmpty()){
                    signUpName.error="Enter your name"
                }
                if(email.isEmpty()){
                    signUpEmail.error="Enter your email correct"
                }
                if(phone.isEmpty()){
                    signUpPhone.error="Enter your phone number"
                }
                if(password.isEmpty()){
                    signUpPassword.error = "Your password dont is empty"
                }
                if(passwordc.isEmpty()){
                    signUpCPassword.error ="Your password don't matched"
                }
                Toast.makeText(requireActivity(),"Something went wrong",Toast.LENGTH_LONG).show()
            }else if (!email.matches(emailPattern.toRegex())){
                signUpEmail.error = "Incorrect email"
            }
//            else if(phone.length == 10){
//                signUpPhone.error = "Incorrect phone number"
//                Toast.makeText(requireActivity(),"number of phone number incorrect",Toast.LENGTH_SHORT).show()
//            }
        else if(password.length<6){
                signUpPassword.error ="password should be greater than 6 characters"
            }else if (password!=passwordc){
                signUpCPassword.error="password don't matches"
                Toast.makeText(requireActivity(),"password don't matches",Toast.LENGTH_SHORT).show()
            }
            else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    Log.e("B", "hust" )
                    if(it.isSuccessful){
                        val databaseReference = FirebaseDatabase.getInstance().reference
                        val database = databaseReference.child("users").child(auth.currentUser!!.uid)
                        val users:Users = Users(name,email,phone,auth.currentUser!!.uid)
                        database.setValue(users).addOnCompleteListener{
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
            }
        }
        return view
    }

}