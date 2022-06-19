package com.example.Shopping_Wish_List.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.Shopping_Wish_List.Navigable
import com.example.Shopping_Wish_List.databinding.FragmentEditBinding
import com.example.Shopping_Wish_List.databinding.FragmentSnapBinding

class SnapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return FragmentSnapBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    private lateinit var binding: FragmentSnapBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.picButton.setOnClickListener {
            (activity as? Navigable)?.navigate(Navigable.Destination.Pic)
        }
    }
}