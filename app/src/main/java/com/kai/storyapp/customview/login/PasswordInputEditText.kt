package com.kai.storyapp.customview.login

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.kai.storyapp.R

class PasswordInputEditText : AppCompatEditText {

    private lateinit var input: Drawable

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        input = ContextCompat.getDrawable(context, R.drawable.shape_login_input) as Drawable

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setEditTextErrorInputMessage()
            }
        })
    }

    private fun setEditTextErrorInputMessage(){
        if(text.toString().length < 8){
            error = context.getString(R.string.password_more_than_8)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = input
    }

}