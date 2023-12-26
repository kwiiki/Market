package com.example.market

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.market.SignViewModel
import com.example.market.R
import com.example.market.RegistrationFragment

class SignFragment : Fragment() {
    private lateinit var signInEmail: EditText
    private lateinit var signInPassword: EditText
    private lateinit var signInBtn: Button
    private lateinit var signInText: TextView
    private lateinit var viewModel: SignViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        viewModel = ViewModelProvider(this).get(SignViewModel::class.java)

        signInEmail = view.findViewById(R.id.signInEmail)
        signInPassword = view.findViewById(R.id.signInPassword)
        signInBtn = view.findViewById(R.id.singInButton)
        signInText = view.findViewById(R.id.signInText)

        signInText.setOnClickListener {
            val registrationFragment = RegistrationFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, registrationFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        signInBtn.setOnClickListener {
            val email = signInEmail.text.toString()
            val password = signInPassword.text.toString()

            Toast.makeText(requireActivity(),"SignIn was successful",Toast.LENGTH_SHORT).show()
            viewModel.signInWithEmailAndPassword(email, password, {
                viewModel.saveUserLoggedInState(requireContext())
                val homeFragment = HomeFragment()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView, homeFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }, { error ->
                Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show()
            })
        }

        return view
    }
}
