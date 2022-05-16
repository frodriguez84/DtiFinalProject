package com.example.proyectofinal.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.proyectofinal.R


import com.example.proyectofinal.viewmodels.HomeViewModel

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private lateinit var v : View

    private lateinit var viewModel: HomeViewModel

    private lateinit var userMail : String
    private lateinit var mailText : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       v =inflater.inflate(R.layout.fragment_home, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()

    //  userMail = HomeFragmentArgs.fromBundle(requireArguments()).mailUser

      //  mailText = v.findViewById(R.id.emailText)
      //  mailText.text = userMail

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

    }

}