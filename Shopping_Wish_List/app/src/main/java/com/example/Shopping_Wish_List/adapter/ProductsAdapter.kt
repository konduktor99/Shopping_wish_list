package com.example.Shopping_Wish_List.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.HandlerCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.Shopping_Wish_List.Navigable
import com.example.Shopping_Wish_List.model.Product
import com.example.Shopping_Wish_List.databinding.FragmentListBinding
import com.example.Shopping_Wish_List.databinding.ListItemBinding

import kotlin.properties.Delegates

class ProductViewHolder(val binding : ListItemBinding) : RecyclerView.ViewHolder(binding.root){


    fun bind(product: Product){

        binding.title.text = product.title
        binding.localization.text = product.localization
        binding.imageView.setImageBitmap(product.img)
    }
}


class ProductsAdapter : RecyclerView.Adapter<ProductViewHolder>() {

    lateinit var listFragmentBiding : FragmentListBinding
    val handler : Handler = HandlerCompat.createAsync(Looper.getMainLooper())

    var clickedPos by Delegates.notNull<Int>()



    private val data = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val binding =ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding).also { vh ->
            binding.root.setOnClickListener {

//                clickedPos=vh.layoutPosition
                clickedPos =data[vh.layoutPosition].id
                (parent.context as? Navigable)?.navigate(Navigable.Destination.Edit)


            }

        }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
       holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun replace(newData: List<Product>){

        data.clear()
        data.addAll(newData)
        handler.post{
            notifyDataSetChanged()
        }

    }



}