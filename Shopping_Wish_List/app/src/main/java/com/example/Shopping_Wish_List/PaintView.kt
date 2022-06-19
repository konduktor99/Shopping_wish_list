package com.example.Shopping_Wish_List

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.Shopping_Wish_List.model.PathWithSettings
import com.example.Shopping_Wish_List.model.Settings

class PaintView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    var color: Int = Color.BLACK
    var size: Float = 30f
    private val paths = mutableListOf<PathWithSettings>()

    var background: Bitmap? = null
        set(value) {
            field = value
            invalidate()
        }
    private val defaultPaint: Paint = Paint()

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }
    override fun onDraw(canvas: Canvas) {
        drawBackground(canvas)
        drawPaths(canvas)
    }
    private fun drawBackground(canvas: Canvas){
        background?.let{
            val rect = Rect(0,0,height,width)
            canvas.drawBitmap(it,null,rect,defaultPaint)

        }
    }

    private fun drawPaths(canvas: Canvas){

        paths.forEach{
            paint.apply {
                strokeWidth = it.settings.size
                color = it.settings.color
            }
            canvas.drawPath(it.path,paint )
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean{

        event ?: return true

        when(event.action){
            MotionEvent.ACTION_DOWN ->{
                PathWithSettings(settings = Settings(color, size)).let{
                    it.path.moveTo(event.x,event.y)
                    it.path.lineTo(event.x,event.y)
                    paths.add(it)
                }
            }
            MotionEvent.ACTION_MOVE ->{
                paths.last().path.lineTo(event.x,event.y)
            }
        }


        invalidate()
        return true
    }
    fun generateBitmap(bitmap: Bitmap) : Bitmap {

//        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        drawPaths(canvas)
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap,height, width, false)

        return resizedBitmap
    }
}