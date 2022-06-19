package com.example.Shopping_Wish_List.Fragment

import android.R.attr.bitmap
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import androidx.fragment.app.Fragment
import com.example.Shopping_Wish_List.Navigable
import com.example.Shopping_Wish_List.databinding.FragmentPaintBinding
import com.example.Shopping_Wish_List.model.Settings
import java.io.File
import java.util.*


class PaintFragment : Fragment() {

    private lateinit var binding: FragmentPaintBinding
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private var imageUri: Uri? = null


    private val onTakePhoto:  (Boolean) -> Unit =
        { photography: Boolean ->
            if (!photography) {
                imageUri?.let {
                    requireContext().contentResolver.delete(it, null, null)
                }
            } else {

                loadBitmap()
            }
        }
    private fun loadBitmap(){
        val imageUri = imageUri ?: return


        requireContext().contentResolver.openInputStream(imageUri)?.use {
            BitmapFactory.decodeStream(it)
        }?.let {
            binding.paintView.background =  it
        }

        (activity as? Navigable)?.navigate(Navigable.Destination.Paint)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraLauncher = registerForActivityResult(
            ActivityResultContracts.TakePicture(),
            onTakePhoto
        )

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaintBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        createPicture()
    }

    private fun createPicture() {
        val imagesUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val ct = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_  ${Calendar.getInstance().getTime()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        imageUri = requireContext().contentResolver.insert(imagesUri, ct)
        cameraLauncher.launch(imageUri)

//        val imageUri = imageUri ?: return
//        val inStream =requireContext().contentResolver.openInputStream(imageUri)
//
//        val ei = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            ExifInterface(File(imageUri?.path))
//        } else {
//            inStream?.let { ExifInterface(it) };
//        }
//        val orientation: Int = ei!!.getAttributeInt(
//            ExifInterface.TAG_ORIENTATION,
//            ExifInterface.ORIENTATION_UNDEFINED
//        )
//
//        var rotatedBitmap: Bitmap? = null
//
//
//        var bitmap = BitmapFactory.decodeStream(inStream)
//        when (orientation) {
//            ExifInterface.ORIENTATION_ROTATE_90 -> rotatedBitmap = rotateImage(bitmap, 90F)
//            ExifInterface.ORIENTATION_ROTATE_180 -> rotatedBitmap = rotateImage(bitmap, 180F)
//            ExifInterface.ORIENTATION_ROTATE_270 -> rotatedBitmap = rotateImage(bitmap, 270F)
//            ExifInterface.ORIENTATION_NORMAL -> rotatedBitmap = bitmap
//            else -> rotatedBitmap = bitmap
//        }
//        requireContext().contentResolver.openOutputStream(imageUri)?.use{
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it)
//        }
        }

//    fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
//        val matrix = Matrix()
//        matrix.postRotate(angle)
//        return Bitmap.createBitmap(
//            source, 0, 0, source.width, source.height,
//            matrix, true
//        )
//    }


        fun setSettings(settings: Settings) {
            binding.paintView.apply {
                color = settings.color
                size = settings.size
            }
        }

        fun save(): Bitmap? {
            val imageUri = imageUri ?: return Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888)
            val inStream = requireContext().contentResolver.openInputStream(imageUri)
            var imBitmap = BitmapFactory.decodeStream(inStream)
            var bitmap = imBitmap.copy(Bitmap.Config.ARGB_8888,true);
            bitmap = binding.paintView.generateBitmap(bitmap)
            requireContext().contentResolver.openOutputStream(imageUri)?.use{
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it)
            }

            return bitmap
        }


}