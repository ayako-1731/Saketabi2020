package com.example.saketabi.ui.details

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.example.saketabi.R
import com.example.saketabi.data.LabelData
import com.facebook.drawee.view.SimpleDraweeView
import com.google.gson.Gson
import com.star_zero.eternalviewpager.EternalViewPager
import java.io.File


class DetailsFragment : Fragment {

    constructor() : super(){

    }

    private lateinit var mData : List<LabelData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val gson = Gson()
        mData = gson.fromJson(requireArguments().getString("data"),Array<LabelData>::class.java).toList()
        val view = inflater.inflate(R.layout.fragment_details, container, false)


        view.findViewById<EternalViewPager>(R.id.viewPager).setAdapter(DetailViewPagerAdapter(requireActivity().supportFragmentManager,requireArguments().getInt("index"),mData))
        return view
    }

}