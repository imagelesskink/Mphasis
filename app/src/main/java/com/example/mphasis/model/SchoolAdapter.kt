package com.example.mphasis.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mphasis.databinding.SchoolItemBinding
import com.example.mphasis.model.School

class SchoolAdapter(
    private val itemClicked: ItemClickedListener,
    private val schoolData: MutableList<School> = mutableListOf()
) : RecyclerView.Adapter<SchoolViewHolder>() {

    // insert the schools list retrieved from API query
    fun setSchoolsList(schools: List<School>) {
        schoolData.clear()
        schoolData.addAll(schools)
        notifyItemRangeChanged(0, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder =
        SchoolViewHolder(
            SchoolItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClicked
        )

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) =
        holder.bind(schoolData[position])

    override fun getItemCount(): Int = schoolData.size
}

class SchoolViewHolder(
    private val binding: SchoolItemBinding,
    private val itemClicked: ItemClickedListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(school: School) {
        binding.apply {
            tvSchoolName.text = school.schoolName
            tvLocation.text = school.neighborhood
            btnReadMore.setOnClickListener {
                itemClicked.onItemClicked(school)
            }
        }
    }
}

interface ItemClickedListener {
    fun onItemClicked(school: School)
}