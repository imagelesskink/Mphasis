package com.example.mphasis.util

import android.app.AlertDialog
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mphasis.viewmodel.SchoolViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
open class BaseFragment : Fragment() {
    protected val schoolsViewModel: SchoolViewModel by activityViewModels()


    fun displayErrors(
        message: String = "Working on issues",
        retry: () -> Unit
    ) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error!")
            .setPositiveButton("Retry") { dialog, _ ->
                dialog.dismiss()
                retry()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setMessage(message)
            .create()
            .show()
    }

    fun showToastMessage(message: String) =
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}