package com.example.Shopping_Wish_List.Fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Shopping_Wish_List.data.DataSource
import com.example.Shopping_Wish_List.Navigable
import com.example.Shopping_Wish_List.adapter.ProductsAdapter
import com.example.Shopping_Wish_List.data.ProductDatabase
import com.example.Shopping_Wish_List.databinding.FragmentListBinding
import com.example.Shopping_Wish_List.model.Product
import kotlin.concurrent.thread


class ListFragment : Fragment() {

private lateinit var binding : FragmentListBinding
 lateinit var adapter : ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentListBinding.inflate(inflater, container, false).also{
            binding=it
        }.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ProductsAdapter().apply {
            listFragmentBiding = binding
        }

        loadData()

        binding.list.let{
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())

        }


        binding.addButton.setOnClickListener{
            (activity as? Navigable)?.navigate(Navigable.Destination.Snap)
        }



    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun loadData() =
        thread {
            val products =ProductDatabase.open(requireContext()).products.getAll().map{
                    entity -> Product(entity.id,entity.title, entity.localization,
                BitmapFactory.decodeByteArray(entity.image, 0, entity.image.size))
            }
            adapter.replace(products)
        }

}