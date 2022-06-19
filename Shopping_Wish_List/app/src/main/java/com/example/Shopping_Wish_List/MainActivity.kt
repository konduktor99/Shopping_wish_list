package com.example.Shopping_Wish_List

import android.database.CursorWindow
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.Shopping_Wish_List.Fragment.*
import java.lang.reflect.Field


class MainActivity : AppCompatActivity(), Navigable {

    private lateinit var listFragment: ListFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        try {
            val field: Field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.setAccessible(true)
            field.set(null, 100 * 1024 * 1024) //the 100MB is the new size
        } catch (e: Exception) {
            e.printStackTrace()
        }

       listFragment= ListFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.container,listFragment, listFragment.javaClass.name).commit()
    }

    override fun navigate(to: Navigable.Destination) {

        supportFragmentManager.beginTransaction().apply {
            when(to){
                Navigable.Destination.List ->
                    replace(R.id.container, listFragment, listFragment.javaClass.name)
                Navigable.Destination.Add -> {
                    replace(R.id.container, EditFragment(null), EditFragment::class.java.name)
                    }
                Navigable.Destination.Edit -> {
                    replace(R.id.container, EditFragment(listFragment.adapter.clickedPos), EditFragment::class.java.name)
                    }
                Navigable.Destination.Snap -> {
                    replace(R.id.container, SnapFragment(), SnapFragment::class.java.name)
                }
                Navigable.Destination.Pic -> {
                    replace(R.id.container, PaintFragment(), PaintFragment::class.java.name)
                }
                Navigable.Destination.Paint -> {
                    add(R.id.container, SettingsFragment(), SettingsFragment::class.java.name)
                }
            }

            addToBackStack(EditFragment::class.java.name)
            addToBackStack(SnapFragment::class.java.name)
            addToBackStack(PaintFragment::class.java.name)


        }.commit()

    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main,menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when(item.itemId){
//            R.id.settings -> {
//                true
//            }
//            else ->super.onOptionsItemSelected(item)
//        }
//    }


}