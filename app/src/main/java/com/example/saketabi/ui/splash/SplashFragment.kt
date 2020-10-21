package com.example.saketabi.ui.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saketabi.Commons
import com.example.saketabi.MainActivity
import com.example.saketabi.R

class SplashFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        handler = Handler(context.mainLooper)
        if(context is MainActivity)
            parent = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onStart() {
        super.onStart()
        val user  = Commons.auth.currentUser
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        handler.postDelayed({
            if(Commons.user == null)
                parent.navigateFromSplashToLogin()
            else
                parent.navigateFromSplashToHome()
        },3000);
    }

    lateinit var handler : Handler
    lateinit var parent : MainActivity


}