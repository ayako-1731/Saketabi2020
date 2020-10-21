package com.example.saketabi.view

import android.content.Context
import android.util.AttributeSet
import com.example.saketabi.R
import com.twitter.sdk.android.core.identity.TwitterLoginButton


class CustomTwitterLoginButton : TwitterLoginButton {

    constructor(context: Context) : this(context,null) {
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,android.R.attr.buttonStyle){

    }

    constructor(context: Context,attrs: AttributeSet?,defStyle: Int) : super (context,attrs,defStyle){
        init()
    }

    private fun init(){
        setText("Twitterでログイン")
        setTextSize(14.0f)
        setPadding(0,0,0,0)
        setBackgroundResource(R.drawable.shape_rounded_corners)
    }
}