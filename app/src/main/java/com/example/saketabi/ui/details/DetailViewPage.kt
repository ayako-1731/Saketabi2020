package com.example.saketabi.ui.details

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.saketabi.MainActivity
import com.example.saketabi.R
import com.example.saketabi.data.LabelData
import com.facebook.drawee.view.SimpleDraweeView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class DetailViewPage  : Fragment{
    constructor() : super(){

    }

    private lateinit var mData : LabelData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.viewpager_item,container,false)

        val gson = Gson()
        mData = gson.fromJson(requireArguments().getString("data"),LabelData::class.java)

        view.findViewById<SimpleDraweeView>(R.id.imageview).setImageBitmap(BitmapFactory.decodeStream(requireActivity().openFileInput(mData.filePath)))

        val group = view.findViewById<ChipGroup>(R.id.tags)
        val type = Chip(requireContext(),null,R.attr.TypeChip)
        type.text = mData.type
        group.addView(type)
        for(flavor in mData.flavor)
        {
            val flavorChip = Chip(requireContext(),null,R.attr.FlavorChip)
            flavorChip.text = flavor
            group.addView(flavorChip)
        }

        view.findViewById<TextView>(R.id.labelName).text = mData.labelName

        view.findViewById<TextView>(R.id.comment).text = mData.comment

        view.findViewById<ImageButton>(R.id.deleteButton).setOnClickListener {
            val activity = (requireActivity() as MainActivity)
            val json = activity.getPreferences(Context.MODE_PRIVATE).getString("Data","")
            val gson2 = Gson()
            val data = gson2.fromJson(json,Array<LabelData>::class.java)
            val datalist = data.toMutableList()
            datalist.removeAll {  it.filePath == mData.filePath }
            val deleteFile = File(requireActivity().filesDir.absolutePath + "/" + mData.filePath)
            deleteFile.delete()

            activity.getPreferences(Context.MODE_PRIVATE).edit().putString("Data",gson.toJson(datalist.toTypedArray(),Array<LabelData>::class.java)).apply()

            activity.navigateFromDetailsToHome()
        }

        view.findViewById<ImageButton>(R.id.shareButton).setOnClickListener {
            val labelData = mData
            val tempFile = File(requireActivity().externalCacheDir?.absolutePath + "/tempFile.jpg")
            if(tempFile.exists())
                tempFile.delete()
            val file = File(requireActivity().filesDir.absolutePath + "/${labelData.filePath}").copyTo(tempFile)
            val uri = FileProvider.getUriForFile(requireContext(),requireActivity().packageName + ".provider",file)

            val builder = ShareCompat.IntentBuilder.from(requireActivity())
            builder
                .setStream(uri)
                .setType("image/jpeg")

            builder.startChooser()
        }
        when(mData.levelOfFavorite){
            1 -> {
                view.findViewById<ImageView>(R.id.favorite1).setImageResource(R.drawable.heart)
            }
            2 -> {
                view.findViewById<ImageView>(R.id.favorite1).setImageResource(R.drawable.heart)
                view.findViewById<ImageView>(R.id.favorite2).setImageResource(R.drawable.heart)
            }
            3 -> {
                view.findViewById<ImageView>(R.id.favorite1).setImageResource(R.drawable.heart)
                view.findViewById<ImageView>(R.id.favorite2).setImageResource(R.drawable.heart)
                view.findViewById<ImageView>(R.id.favorite3).setImageResource(R.drawable.heart)
            }
            4 -> {
                view.findViewById<ImageView>(R.id.favorite1).setImageResource(R.drawable.heart)
                view.findViewById<ImageView>(R.id.favorite2).setImageResource(R.drawable.heart)
                view.findViewById<ImageView>(R.id.favorite3).setImageResource(R.drawable.heart)
                view.findViewById<ImageView>(R.id.favorite4).setImageResource(R.drawable.heart)
            }
            5-> {
                view.findViewById<ImageView>(R.id.favorite1).setImageResource(R.drawable.heart)
                view.findViewById<ImageView>(R.id.favorite2).setImageResource(R.drawable.heart)
                view.findViewById<ImageView>(R.id.favorite3).setImageResource(R.drawable.heart)
                view.findViewById<ImageView>(R.id.favorite4).setImageResource(R.drawable.heart)
                view.findViewById<ImageView>(R.id.favorite5).setImageResource(R.drawable.heart)
            }
        }
        view.findViewById<TextView>(R.id.date).text = SimpleDateFormat("MM/dd", Locale.JAPAN).format(mData.saveDate)

        view.findViewById<ConstraintLayout>(R.id.parent).setBackgroundResource(when(mData.labelGenre){
            "格好いい" -> R.color.cool
            "かわいい" -> R.color.cute
            "面白い" -> R.color.`fun`
            "限定ラベル" -> R.color.limited
            "古典的" -> R.color.classic
            "おしゃれ" -> R.color.fashonable
            else -> R.color.white
        })

        return view;
    }


}