package com.example.Shopping_Wish_List.Fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.Shopping_Wish_List.Navigable
import com.example.Shopping_Wish_List.R
import com.example.Shopping_Wish_List.databinding.FragmentSettingsBinding
import com.example.Shopping_Wish_List.model.Settings


class SettingsFragment : Fragment(), SeekBar.OnSeekBarChangeListener{

    private lateinit var binding: FragmentSettingsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSettingsBinding.inflate(inflater,container,false).also {
            binding = it
        }.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.seekBar.setOnSeekBarChangeListener(this)
        binding.colorRadioGroup.setOnCheckedChangeListener { radioGroup, id -> changeSettings() }

        binding.saveButton.setOnClickListener(){
        val paintFragment =(parentFragmentManager.findFragmentByTag(PaintFragment::class.java.name) as? PaintFragment)
        val bitmap = paintFragment?.save()



            val editFragment = EditFragment(null)
            if (bitmap != null) {
                editFragment.productBitmap = bitmap
            }
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, editFragment)?.addToBackStack(null)?.commit()


        }
    }

    private fun idToColor(id: Int): Int =
        when(id) {
            R.id.blackButton -> Color.BLACK
            R.id.redButton -> Color.RED
            R.id.greenButton -> Color.GREEN
            R.id.blueButton -> Color.BLUE
            else -> throw NotImplementedError()
        }


    private fun changeSettings(){
        val settings = Settings(
            idToColor(binding.colorRadioGroup.checkedRadioButtonId),
            binding.seekBar.progress.toFloat())
        (parentFragmentManager.findFragmentByTag(PaintFragment::class.java.name) as? PaintFragment)?.let {
            it.setSettings(settings)
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, value: Int, isUserSet: Boolean) {
        changeSettings()
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {}

    override fun onStopTrackingTouch(p0: SeekBar?) {}

}