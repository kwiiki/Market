package com.example.market

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)

        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userLoggedIn = sharedPreferences.getBoolean("user_logged_in", false)

        // Если пользователь вошел в аккаунт, выбираем кнопку "Profile"
//        if (userLoggedIn) {
//            bottomNavigationView.selectedItemId = R.id.profileFragment
//            bottomNavigationView.removeBadge(R.id.loginFragment)
//        } else {
//            // Если пользователь не вошел в аккаунт, выбираем другую кнопку (например, "Catalog")
//            bottomNavigationView.selectedItemId = R.id.homeFragment
//        }

//        bottomNavigationView.setOnItemSelectedListener { item ->
//            val transaction = supportFragmentManager.beginTransaction()
//            // Обработка выбора кнопки
//            when (item.itemId) {
//                R.id.homeFragment -> {
//                    // Открываем фрагмент или активность с каталогом
//                    val catalogFragment = HomeFragment() // Replace with your actual fragment
//                    transaction.replace(R.id.fragmentContainerView, catalogFragment)
//                    transaction.commit()
//                    true
//                }
//
//                R.id.basketFragment -> {
//                    // Открываем фрагмент или активность с корзиной
//                    val basketFragment = BasketFragment() // Replace with your actual fragment
//                    transaction.replace(R.id.fragmentContainerView, basketFragment)
//                    transaction.commit()
//                    true
//                }
//
//                R.id.loginFragment -> {
//                    // Открываем фрагмент или активность с профилем (ваша текущая логика входа)
//                    val profileFragment = LoginFragment() // Replace with your actual fragment
//                    transaction.replace(R.id.fragmentContainerView, profileFragment)
//                    item.setIcon(R.drawable.profile)
//                    transaction.commit()
//                    true
//                }
//
//                R.id.profileFragment -> {
//                    val profileFragment = ProfileFragment() // Replace with your actual fragment
//                    transaction.replace(R.id.fragmentContainerView, profileFragment)
//                    transaction.commit()// Открываем фрагмент или активность с профилем (может быть не нужно, так как уже в активности)
//                    true
//                }
//
//                else -> false
//            }
//        }
    }
}
