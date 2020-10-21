package com.example.saketabi.ui.add

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.Toast
import com.example.saketabi.MainActivity
import com.example.saketabi.R
import com.example.saketabi.data.LabelData
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null)
            capture = arguments?.getByteArray("capture")!!
    }

    lateinit var capture : ByteArray
    var favoriteLevel = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add, container, false)

        //お気に入りボタンの設定
        val fav1 = view.findViewById<ImageButton>(R.id.favorite1)
        val fav2 = view.findViewById<ImageButton>(R.id.favorite2)
        val fav3 = view.findViewById<ImageButton>(R.id.favorite3)
        val fav4 = view.findViewById<ImageButton>(R.id.favorite4)
        val fav5 = view.findViewById<ImageButton>(R.id.favorite5)
        fav1.setOnClickListener {
            if(favoriteLevel == 1){
                favoriteLevel = 0
                fav1.setImageResource(R.drawable.heart2)
            }
            else {
                favoriteLevel = 1
                fav1.setImageResource(R.drawable.heart)
                fav2.setImageResource(R.drawable.heart2)
                fav3.setImageResource(R.drawable.heart2)
                fav4.setImageResource(R.drawable.heart2)
                fav5.setImageResource(R.drawable.heart2)
            }
        }

        fav2.setOnClickListener {
            if(favoriteLevel == 2){
                favoriteLevel = 1
                fav2.setImageResource(R.drawable.heart2)
            }
            else {
                favoriteLevel = 2
                fav1.setImageResource(R.drawable.heart)
                fav2.setImageResource(R.drawable.heart)
                fav3.setImageResource(R.drawable.heart2)
                fav4.setImageResource(R.drawable.heart2)
                fav5.setImageResource(R.drawable.heart2)
            }
        }

        fav3.setOnClickListener {
            if(favoriteLevel == 3){
                favoriteLevel = 2
                fav3.setImageResource(R.drawable.heart2)
            }
            else {
                favoriteLevel = 3
                fav1.setImageResource(R.drawable.heart)
                fav2.setImageResource(R.drawable.heart)
                fav3.setImageResource(R.drawable.heart)
                fav4.setImageResource(R.drawable.heart2)
                fav5.setImageResource(R.drawable.heart2)
            }
        }

        fav4.setOnClickListener {
            if(favoriteLevel == 4){
                favoriteLevel = 3
                fav4.setImageResource(R.drawable.heart2)
            }
            else {
                favoriteLevel = 4
                fav1.setImageResource(R.drawable.heart)
                fav2.setImageResource(R.drawable.heart)
                fav3.setImageResource(R.drawable.heart)
                fav4.setImageResource(R.drawable.heart)
                fav5.setImageResource(R.drawable.heart2)
            }
        }

        fav5.setOnClickListener {
            if(favoriteLevel == 5){
                favoriteLevel = 4
                fav5.setImageResource(R.drawable.heart2)
            }
            else {
                favoriteLevel = 5
                fav1.setImageResource(R.drawable.heart)
                fav2.setImageResource(R.drawable.heart)
                fav3.setImageResource(R.drawable.heart)
                fav4.setImageResource(R.drawable.heart)
                fav5.setImageResource(R.drawable.heart)
            }
        }


        //確定ボタンの処理の設定
        view.findViewById<MaterialButton>(R.id.saveButton).setOnClickListener {
            val title = view.findViewById<EditText>(R.id.labelName).text.toString()
            if(title == "")
            {
                Toast.makeText(parentActivity,"名称が入力されていません",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val type = when(view.findViewById<ChipGroup>(R.id.types).checkedChipId){
                R.id.type1 -> "純米大吟醸"
                R.id.type2 -> "純米吟醸"
                R.id.type3 -> "特別純米"
                R.id.type4 -> "純米酒"
                R.id.type5 -> "大吟醸酒"
                R.id.type6 -> "吟醸酒"
                R.id.type7 -> "特別本造酒"
                R.id.type8 -> "本醸造酒"
                else -> ""
             }
            if(type == "")
            {
                Toast.makeText(parentActivity,"タイプが選択されていません",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var flavors = mutableListOf<String>()
            for(flavor in view.findViewById<ChipGroup>(R.id.flavors).checkedChipIds){
                when(flavor){
                    R.id.flavor1 -> flavors.add("旨味")
                    R.id.flavor2 -> flavors.add( "濃厚")
                    R.id.flavor3 -> flavors.add("フルーティ")
                    R.id.flavor4 -> flavors.add("辛口")
                    R.id.flavor5 -> flavors.add("甘味")
                    R.id.flavor6 -> flavors.add("どっしり")
                    R.id.flavor7 -> flavors.add("酸味")
                    R.id.flavor8 -> flavors.add("すっきり")
                }
            }

            if(flavors.count() == 0){
                Toast.makeText(parentActivity,"フレーバーが選択されていません",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val comment = view.findViewById<EditText>(R.id.comment).text.toString()

            val label = when(view.findViewById<ChipGroup>(R.id.label_categories).checkedChipId){
                R.id.category1 -> "格好いい"
                R.id.category2 -> "かわいい"
                R.id.category3 -> "面白い"
                R.id.category4 -> "古典的"
                R.id.category5 -> "おしゃれ"
                R.id.category6 -> "限定ラベル"
                else -> ""
            }
            if(label == "")
            {
                Toast.makeText(parentActivity,"ラベルが選択されていません",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fileName =  SimpleDateFormat("yyyyMMddHHmmss").format(Date()) + ".jpg"

            val fo = parentActivity.openFileOutput(fileName,Context.MODE_PRIVATE)
            fo.write(capture)
            fo.close()

            val saveData = LabelData(title,type,flavors.toList(),comment,favoriteLevel,label,fileName,Date(System.currentTimeMillis()))
            val savedData  = parentActivity.getPreferences(Context.MODE_PRIVATE).getString("Data","")

            val gson = Gson()
            val savedObject =
                if(savedData == "")
                    arrayOf()
                else
                    gson.fromJson(savedData,Array<LabelData>::class.java)

            val editObject = savedObject.toMutableList()
            editObject.add(saveData)

            val savingData = gson.toJson(editObject.toTypedArray(),Array<LabelData>::class.java)

            parentActivity.getPreferences(Context.MODE_PRIVATE).edit().putString("Data",savingData).apply()
            Toast.makeText(parentActivity,"ラベルデータを保存しました",Toast.LENGTH_SHORT).show()
            parentActivity.navigateFromAddToHome()
        }
        return view
    }

    lateinit var parentActivity : MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainActivity)
            parentActivity = context
    }
}