package com.example.kinopoisk

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.kinopoisk.fragments.FirstFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isNetworkAvailable(this)) {
            openFirstFragment()
        } else {
            Toast.makeText(
                this,
                NO_INTERNET,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun openFirstFragment() {
        val firstFragment: Fragment = FirstFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.animator.flip_right_in,
                R.animator.flip_right_out,
                R.animator.flip_left_in,
                R.animator.flip_left_out
            ).replace(R.id.container, firstFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }

}