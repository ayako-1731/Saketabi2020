package com.example.saketabi.ui.home

import android.content.Context
import android.media.Image
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saketabi.MainActivity
import com.example.saketabi.R
import com.example.saketabi.data.LabelData
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        if(context is MainActivity)
            parentActivity = context
        super.onAttach(context)
    }

    lateinit var parentActivity : MainActivity
    lateinit var allData : List<LabelData>
    lateinit var serchedData : List<LabelData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        view.findViewById<FloatingActionButton>(R.id.cameraButton).setOnClickListener {
            parentActivity.navigateFromHomeToCamera()
        }
        val savedData = parentActivity.getPreferences(Context.MODE_PRIVATE).getString("Data","")
        val gson = Gson()
        var data = if(savedData == "") arrayOf() else gson.fromJson(savedData,Array<LabelData>::class.java)
        allData = data.toList()
        val rv = view.findViewById<RecyclerView>(R.id.recyclerView)
        val llm = LinearLayoutManager(parentActivity)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv.setHasFixedSize(true)
        rv.layoutManager = llm
        rv.adapter = HomeAdapter(parentActivity,data.toList())

        view.findViewById<ImageButton>(R.id.searchButton).setOnClickListener {
            val box = view.findViewById<ConstraintLayout>(R.id.searchBox)
            if(box.visibility == View.GONE)
                box.visibility = View.VISIBLE
            else
                box.visibility = View.GONE
        }

        view.findViewById<MaterialButton>(R.id.search).setOnClickListener {
            val searchWords = view.findViewById<EditText>(R.id.searchText).text.toString().split(' ','　')
            serchedData = allData.filter { true }
            for(word in searchWords){
                val data2 = serchedData.filter { true }
                serchedData = data2.filter { it.labelName.contains(word) || it.flavor.contains(word) || it.type.contains(word) || it.comment.contains(word) || it.labelGenre.contains(word) }
            }
            val rv2 = view.findViewById<RecyclerView>(R.id.recyclerView)
            val llm2 = LinearLayoutManager(parentActivity)
            llm2.orientation = LinearLayoutManager.VERTICAL
            rv2.setHasFixedSize(true)
            rv2.layoutManager = llm
            rv2.adapter = HomeAdapter(parentActivity,serchedData)
            rv2.setBackgroundResource(R.color.white)
            view.findViewById<ChipGroup>(R.id.category).clearCheck()
            view.findViewById<ChipGroup>(R.id.category).setBackgroundResource(R.color.white)

        }

        view.findViewById<ChipGroup>(R.id.category).setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.all -> {
                    val data2 = allData.filter { true }
                    val rv2 = view.findViewById<RecyclerView>(R.id.recyclerView)
                    val llm2 = LinearLayoutManager(parentActivity)
                    llm2.orientation = LinearLayoutManager.VERTICAL
                    rv2.setHasFixedSize(true)
                    rv2.layoutManager = llm
                    rv2.adapter = HomeAdapter(parentActivity,data2)
                    rv2.setBackgroundResource(R.color.white)
                    group.setBackgroundResource(R.color.white)
                }
                R.id.cool ->{
                    val data2 = allData.filter { it.labelGenre == "格好いい" }
                    val rv2 = view.findViewById<RecyclerView>(R.id.recyclerView)
                    val llm2 = LinearLayoutManager(parentActivity)
                    llm2.orientation = LinearLayoutManager.VERTICAL
                    rv2.setHasFixedSize(true)
                    rv2.layoutManager = llm
                    rv2.adapter = HomeAdapter(parentActivity,data2)
                    rv2.setBackgroundResource(R.color.cool)
                    group.setBackgroundResource(R.color.cool)
                }
                R.id.cute -> {
                    val data2 = allData.filter { it.labelGenre == "かわいい" }
                    val rv2 = view.findViewById<RecyclerView>(R.id.recyclerView)
                    val llm2 = LinearLayoutManager(parentActivity)
                    llm2.orientation = LinearLayoutManager.VERTICAL
                    rv2.setHasFixedSize(true)
                    rv2.layoutManager = llm
                    rv2.adapter = HomeAdapter(parentActivity,data2)
                    rv2.setBackgroundResource(R.color.cute)
                    group.setBackgroundResource(R.color.cute)
                }
                R.id.funny ->{
                    val data2 = allData.filter { it.labelGenre == "面白い" }
                    val rv2 = view.findViewById<RecyclerView>(R.id.recyclerView)
                    val llm2 = LinearLayoutManager(parentActivity)
                    llm2.orientation = LinearLayoutManager.VERTICAL
                    rv2.setHasFixedSize(true)
                    rv2.layoutManager = llm
                    rv2.adapter = HomeAdapter(parentActivity,data2)
                    rv2.setBackgroundResource(R.color.`fun`)
                    group.setBackgroundResource(R.color.`fun`)
                }
                R.id.classic ->{
                    val data2 = allData.filter { it.labelGenre == "古典的" }
                    val rv2 = view.findViewById<RecyclerView>(R.id.recyclerView)
                    val llm2 = LinearLayoutManager(parentActivity)
                    llm2.orientation = LinearLayoutManager.VERTICAL
                    rv2.setHasFixedSize(true)
                    rv2.layoutManager = llm
                    rv2.adapter = HomeAdapter(parentActivity,data2)
                    rv2.setBackgroundResource(R.color.classic)
                    group.setBackgroundResource(R.color.classic)
                }
                R.id.fashonable -> {
                    val data2 = allData.filter { it.labelGenre == "おしゃれ" }
                    val rv2 = view.findViewById<RecyclerView>(R.id.recyclerView)
                    val llm2 = LinearLayoutManager(parentActivity)
                    llm2.orientation = LinearLayoutManager.VERTICAL
                    rv2.setHasFixedSize(true)
                    rv2.layoutManager = llm
                    rv2.adapter = HomeAdapter(parentActivity,data2)
                    rv2.setBackgroundResource(R.color.fashonable)
                    group.setBackgroundResource(R.color.fashonable)
                }
                R.id.limited -> {
                    val data2 = allData.filter { it.labelGenre == "限定ラベル" }
                    val rv2 = view.findViewById<RecyclerView>(R.id.recyclerView)
                    val llm2 = LinearLayoutManager(parentActivity)
                    llm2.orientation = LinearLayoutManager.VERTICAL
                    rv2.setHasFixedSize(true)
                    rv2.layoutManager = llm
                    rv2.adapter = HomeAdapter(parentActivity,data2)
                    rv2.setBackgroundResource(R.color.limited)
                    group.setBackgroundResource(R.color.limited)
                }
            }
        }
        return view
    }


}