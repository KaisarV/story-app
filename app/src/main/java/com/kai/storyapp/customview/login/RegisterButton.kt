package com.kai.storyapp.customview.login

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.kai.storyapp.R

class RegisterButton: AppCompatButton {

    private lateinit var enabledRegisterButton: Drawable
    private lateinit var disabledRegisterButton: Drawable
    private var txtColor: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = if(isEnabled) enabledRegisterButton else disabledRegisterButton
        setTextColor(txtColor)
    }

    private fun init() {
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
        enabledRegisterButton = ContextCompat.getDrawable(context, R.drawable.shape_register_button) as Drawable
        disabledRegisterButton = ContextCompat.getDrawable(context, R.drawable.shape_register_button_disable) as Drawable
    }
}