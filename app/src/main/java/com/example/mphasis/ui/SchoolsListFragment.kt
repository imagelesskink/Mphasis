package com.example.mphasis.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mphasis.R
import com.example.mphasis.databinding.FragmentSchoolsListBinding
import com.example.mphasis.model.ItemClickedListener
import com.example.mphasis.model.School
import com.example.mphasis.util.BaseFragment
import com.example.mphasis.model.SchoolAdapter
import com.example.mphasis.networking.ResponseState

/**
 * A simple [Fragment] subclass.
 *
 * This shows the list of all NYC High Schools
 * This list is implemented using a recyclerview inside which each school represents a card view
 */
class SchoolsListFragment : BaseFragment(), ItemClickedListener {

    private val binding by lazy {
        FragmentSchoolsListBinding.inflate(layoutInflater)
    }
    private val mAdapter by lazy {
        SchoolAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Apply the linear vertical oriented layout manager to the recycler view
        // And attach its adapter
        binding.schoolsRecycler.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = mAdapter
        }

        // Observe the list of school data and populate the view based on the network response
        // If time allowed, will populate local data when device is offline
        schoolsViewModel.schools.observe(viewLifecycleOwner) { state ->
            when(state) {
                is ResponseState.Loading -> {
                    showToastMessage("LOADING")
                    binding.progressBar.visibility = View.VISIBLE
                    binding.schoolsRecycler.visibility = View.GONE
                    binding.swipeRefresh.visibility = View.GONE
                }
                is ResponseState.Success<*> -> {
                    val retrievedSongs = state.response as List<School>
                    binding.progressBar.visibility = View.GONE
                    binding.schoolsRecycler.visibility = View.VISIBLE
                    binding.swipeRefresh.visibility = View.VISIBLE

                    // set the fetched data to the adapter to populate the recyclerview
                    mAdapter.setSchoolsList(retrievedSongs)
                }
                is ResponseState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.schoolsRecycler.visibility = View.GONE
                    binding.swipeRefresh.visibility = View.GONE

                    displayErrors(state.msg.localizedMessage) {
                        schoolsViewModel.getAll()
                    }
                }
            }
        }

        schoolsViewModel.getAll()

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.swipeRefresh.apply {
            setColorSchemeResources(
                android.R.color.holo_blue_dark,
                android.R.color.holo_purple,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_bright,
                android.R.color.holo_red_light,
                android.R.color.holo_green_dark,
            )
            setOnRefreshListener {
                schoolsViewModel.getAll()
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    /**
     * Navigate to the details screen to view selected School's SAT scores and more details obout it
     */
    override fun onItemClicked(school: School) {
        // set info of selected data to show on details screen
        schoolsViewModel.school = school
        // reset the score livedata to retrieve the updated score based on the school user selects
        schoolsViewModel.resetScore()
        findNavController().navigate(R.id.action_schoolsList_to_schoolDetails)
    }
}