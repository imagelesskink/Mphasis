package com.example.mphasis.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mphasis.R
import com.example.mphasis.databinding.FragmentSchoolDetailsBinding
import com.example.mphasis.model.School
import com.example.mphasis.model.Sat
import com.example.mphasis.util.BaseFragment
import com.example.mphasis.networking.ResponseState

/**
 * A simple [Fragment] subclass.
 * this view showw when user click on the 'Read more' button on a item of the school list
 */
class SchoolDetailsFragment : BaseFragment() {
        private val binding by lazy {
            FragmentSchoolDetailsBinding.inflate(layoutInflater)
        }
//    private var _binding: FragmentSchoolDetailsBinding? = null
//    private val binding: FragmentSchoolDetailsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        _binding = FragmentSchoolDetailsBinding.inflate(layoutInflater)

        val schoolInfo = schoolsViewModel.school

        // Populate the view based on network response and update the scores
        schoolsViewModel.satScore.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseState.Loading -> {
                    showToastMessage("Loading More Info")
                }
                is ResponseState.Success<*> -> {
                    val scoreResponse = state.response as List<Sat>
                    val score: Sat? = scoreResponse.firstOrNull()
                    if (scoreResponse.isEmpty()) {
                        displayErrors("No SAT score available") {

                        }
                        binding.apply {
                            if (schoolInfo != null) {
                                schoolName.text = schoolInfo.schoolName
                                tvAddress.text = "Address: ${schoolInfo.location}"
                                tvEmail.text = "Email: ${schoolInfo.schoolEmail}"
                                tvWebsite.text = "Website: ${schoolInfo.website}"
                                tvOverview.text = schoolInfo.overview_paragraph
                            }
                        }
                    } else {
                        binding.apply {
                            if (schoolInfo != null) {
                                schoolName.text = schoolInfo.schoolName

                                scoreInfo.visibility = View.VISIBLE

                                tvSatScores.text =
                                    "SAT Test takers: ${score?.testTakers}"
                                tvMathScores.text = "Math: ${score?.avgMath}"
                                tvReadingScores.text =
                                    "Reading: ${score?.avgReading}"
                                tvWritingScores.text =
                                    "Writing: ${score?.avgWriting}"

                                tvAddress.text = "Address: ${schoolInfo.location}"
                                tvEmail.text = "Email: ${schoolInfo.schoolEmail}"
                                tvWebsite.text = "Website: ${schoolInfo.website}"
                                tvOverview.text = schoolInfo.overview_paragraph
                            }
                        }
                    }
                }
                is ResponseState.Error -> {
                    displayErrors(state.msg.localizedMessage) {}
                }
            }
        }
        if (schoolInfo != null) {
            schoolsViewModel.getSat(schoolInfo.dbn)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }
}
