package com.example.Shopping_Wish_List.Fragment

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.location.Location


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.Shopping_Wish_List.*
import com.example.Shopping_Wish_List.data.DataSource
import com.example.Shopping_Wish_List.data.ProductDatabase
import com.example.Shopping_Wish_List.databinding.FragmentEditBinding
import com.example.Shopping_Wish_List.model.Product
import com.example.Shopping_Wish_List.model.ProductEntity
import com.google.android.gms.location.*
import kotlin.concurrent.thread


class EditFragment(clickedPos : Int?) : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private var clickedPosition :Int? =clickedPos
    private lateinit var product : ProductEntity
    lateinit var productBitmap : Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {






        // Inflate the layout for this fragment
        return FragmentEditBinding.inflate(inflater, container, false).also {
            binding = it
        }.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        if(clickedPosition!=null){
            thread {
                 product =
                    ProductDatabase.open(requireContext()).products.getProduct(clickedPosition!!)


//            product = DataSource.products.get(clickedPosition!!)
                requireActivity().runOnUiThread{

                    binding.header.text = "Edit product"
                    binding.titleForm.setText(product.title)
                    binding.locationTextView.text = product.localization
                    binding.productPic.setImageBitmap(BitmapFactory.decodeByteArray(product.image, 0, product.image.size))
                    binding.deleteButton.visibility = View.VISIBLE
                }

            }

        }else {
//            binding.locationTextView.text= fetchLocation()
//            product = DataSource.products.last()
            binding.productPic.setImageBitmap(productBitmap)
        }


        binding.saveButton.setOnClickListener {

            var title = binding.titleForm.text.toString()
            title = Validation.normalizeTitle(title)
            var localization = binding.locationTextView.text.toString()
            var picture = binding.productPic





            if(clickedPosition!=null) {


               deleteElement()
                val newProduct = ProductEntity(
                    id = clickedPosition!!+1,
                    title = title,
                    localization = localization,
                    image =  product.image)


                addElement(newProduct)

            }else{

                val newProduct = ProductEntity(
                    title = title,
                    localization = localization,
                    image = Conversion.bitmapToByteArray( productBitmap) )

                addElement(newProduct)
            }







        }
        binding.deleteButton.setOnClickListener(){
            var builder = AlertDialog.Builder(it.context)
                builder.setTitle("CONFIRM")
                builder.setMessage("Delete this product?")
                builder.setNegativeButton("No"){di,it -> di.cancel()}
                builder.setPositiveButton("Yes") { di, it -> run{
                    deleteElement()
                    (activity as? Navigable)?.navigate(Navigable.Destination.List)}
                }
                builder.show()

        }

        binding.locationButton.setOnClickListener(){
             fetchLocation()

        }
    }



    private fun fetchLocation(){
        var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                101
            )
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {


               Toast.makeText(requireContext()," Device located on ${it.latitude}, ${it.longitude}.",Toast.LENGTH_SHORT).show()
            binding.locationTextView.text =geoCordsToAddress(it)
        }

    }

    fun geoCordsToAddress(location : Location): String {


        val geocoder = Geocoder(requireContext())
        val currentLocation = geocoder.getFromLocation(
            location.latitude,
            location.longitude,
            1
        )

        return currentLocation.first().getAddressLine(0)
    }

    fun deleteElement(){
        thread {
            ProductDatabase.open(requireContext()).products.deleteProduct(clickedPosition!!)
        }
    }

    fun addElement(newProduct: ProductEntity){
        thread {

            ProductDatabase.open(requireContext()).products.addProduct(newProduct)
            (activity as? Navigable)?.navigate(Navigable.Destination.List)
        }
    }
}