package com.example.mphasis.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mphasis.R
import com.example.mphasis.databinding.FragmentMainViewBinding
import com.example.mphasis.util.BaseFragment


class MainViewFragment : BaseFragment() {

    private val binding by lazy {
        FragmentMainViewBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // navigates to the screen that displays the list of schools
        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_mainView_to_schoolsList)
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}